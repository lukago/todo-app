import {DatePipe} from '@angular/common';
import {Component, Input, OnChanges, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {Group} from '../models/group';
import {Pager} from '../models/Pager';
import {Task} from '../models/task';
import {GroupService} from '../services/group.service';
import {PagerService} from '../services/pager.service';
import {SortService} from '../services/sort.service';

@Component({
  selector: 'tasks',
  templateUrl: './tasks.component.html',
  providers: [SortService, DatePipe, PagerService]
})
export class TasksComponent implements OnInit, OnChanges {
  @Input() tasks: Task[] = [];
  @Input() groups: Group[] = [];
  @Input() useDb = true;
  @Input() loaded = false;

  searchedTasks: Task[];
  pagedTasks: Task[] = [];
  pager: Pager;

  showAddMenu = false;
  showWrongInputMsg = false;
  today: string;

  constructor(private groupService: GroupService,
              private router: Router,
              private sortService: SortService,
              private datePipe: DatePipe,
              private pagerService: PagerService) {
  }

  showSearchedTasks(searchedTasks: Observable<Task[]>): void {
    searchedTasks.subscribe(tasks => {
      this.searchedTasks = tasks;
      this.setPage(1);
    });
  }

  ngOnInit(): void {
    if (this.useDb) {
      this.loadGroupsAndTasks();
    }

    this.today = this.datePipe.transform(new Date(), 'yyyy-MM-ddTHH:mm');
  }

  ngOnChanges(): void {
    if (!this.searchedTasks && this.loaded) {
      this.searchedTasks = this.tasks;
      this.pager ? this.setPage(this.pager.currentPage) : this.setPage(1);
    }

    if (this.pager) {
      this.setPage(this.pager.currentPage);
    }
  }

  loadGroupsAndTasks(): void {
    this.loaded = false;
    this.tasks = [];
    this.groups = [];
    this.groupService.getGroups()
      .then(groups => this.groups = groups)
      .then(() => this.groupService.getTasksDistinct()
        .then(tasks => this.tasks = tasks)
        .then(() => this.searchedTasks = this.tasks)
        .then(() => this.setPage(1))
        .then(() => this.loaded = true)
        .catch(e => this.handleError(e)));
  }

  addTask(title: string, startDate: string,
          note: string, priority: number,
          gid: string, status: boolean) {

    if (title.length < 1 || !priority || !startDate || !gid) {
      this.showWrongInputMsg = true;
      return;
    }

    this.showWrongInputMsg = false;
    this.showAddMenu = false;

    this.groupService
      .createTask(this.tasks, title, new Date(startDate),
        note, priority, Number.parseInt(gid), status)
      .then(t => {
        this.tasks.push(t);
        this.searchedTasks = this.tasks;
        this.setPage(1);
      })
      .catch(e => this.handleError(e));
  }

  deleteTask(task: Task): void {
    this.groupService.deleteTask(task.id)
      .catch(e => this.handleError(e));

    this.tasks = this.tasks.filter(t => t !== task);
    this.pagedTasks = this.pagedTasks.filter(t => t !== task);
    this.searchedTasks = this.searchedTasks.filter(t => t !== task);

    if (this.pagedTasks.length < 1) {
      this.pager.pages.pop();
      if (this.pager.currentPage > this.pager.pages.length) {
        this.setPage(this.pager.currentPage - 1);
      } else {
        this.setPage(this.pager.currentPage);
      }
    }
  }

  setPage(page: number) {
    this.pager = this.pagerService.createPager(this.searchedTasks.length, page, 6);
    this.pagedTasks = this.tasks
      .filter(t => this.searchedTasks.find(task => task.id === t.id))
      .slice(this.pager.startIndex, this.pager.endIndex + 1)
      .reverse();
  }

  handleGroupDeleted(gid: number) {
    this.groups.filter(g => g.id !== gid);
    this.tasks = this.tasks.filter(t => t.groupId !== gid);
    this.searchedTasks = this.tasks;
    this.setPage(1);
  }

  handleGroupAdded(gid: number) {
    this.groupService.getGroup(gid)
      .then(group => this.groups.push(group))
      .catch(e => this.handleError(e));
  }

  gotoTaskEditor(id: number) {
    this.router.navigate(['/tasks', id])
      .catch(e => this.handleError(e));
  }

  getGroupName(gid: number): string {
    return this.groups.find(gr => gr.id === gid).title;
  }

  handleError(error: any) {
    this.router.navigateByUrl('/error');
  }

  sortTasksByTitle() {
    this.sortService.sortTasksByTitle(this.tasks);
    this.setPage(this.pager.currentPage);
  }

  sortTasksByGroupName() {
    this.tasks = this.sortService.sortTasksByGroupName(this.tasks, this.groups);
    this.setPage(this.pager.currentPage);
  }

  sortTasksByDate() {
    this.sortService.sortTasksByDate(this.tasks);
    this.setPage(this.pager.currentPage);
  }

  sortTasksByNote() {
    this.sortService.sortTasksByNote(this.tasks);
    this.setPage(this.pager.currentPage);
  }

  sortTasksByPrio() {
    this.sortService.sortTasksByPrio(this.tasks);
    this.setPage(this.pager.currentPage);
  }

  sortTasksByStatus() {
    this.sortService.sortTasksByStatus(this.tasks);
    this.setPage(this.pager.currentPage);
  }
}
