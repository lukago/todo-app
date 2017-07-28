import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {Observable} from 'rxjs/Observable';
import {Group} from '../models/group';
import {Pager} from '../models/Pager';
import {GroupService} from '../services/group.service';
import {PagerService} from '../services/pager.service';
import {SortService} from '../services/sort.service';

@Component({
  selector: 'groups',
  templateUrl: './groups.component.html',
  providers: [SortService, PagerService]
})
export class GroupsComponent implements OnInit {
  @Input() groups: Group[] = [];
  searchedGroups: Group[] = [];
  pagedGroups: Group[] = [];
  pager: Pager;

  @Output() deleted = new EventEmitter<number>();
  @Output() added = new EventEmitter<number>();

  showAddMenu = false;
  showWrongInputMsg = false;
  loaded = false;

  constructor(private groupService: GroupService,
              private router: Router,
              public sortService: SortService,
              private pagerService: PagerService) {
  }

  showSearchedGroups(searchedGroups: Observable<Group[]>): void {
    searchedGroups.subscribe(groups => {
      this.searchedGroups = groups;
      this.setPage(1);
    });
  }

  ngOnInit(): void {
    this.groups = [];
    this.groupService.getGroups()
      .then(groups => this.groups = groups)
      .then(() => this.searchedGroups = this.groups)
      .then(() => this.setPage(1))
      .then(() => this.loaded = true)
      .catch(e => this.handleError(e));
  }

  gotoGroupEditor(id: number): void {
    this.router.navigate(['/groups', id]);
  }

  addGroup(name: string): void {
    if (!name) {
      this.showWrongInputMsg = true;
      return;
    }

    this.showWrongInputMsg = false;
    this.showAddMenu = false;
    name = name.trim();

    this.groupService.createGroup(name)
      .then(group => {
        this.groups.push(group);
        this.searchedGroups = this.groups;
        this.setPage(this.pager.currentPage);
        this.added.emit(group.id);
      })
      .catch(e => this.handleError(e));
  }

  deleteGroup(group: Group): void {
    this.deleted.emit(group.id);
    this.groupService.deleteGroup(group.id)
      .catch(e => this.handleError(e));

    this.groups = this.groups.filter(gr => gr !== group);
    this.pagedGroups = this.pagedGroups.filter(gr => gr !== group);
    this.searchedGroups = this.searchedGroups.filter(gr => gr !== group);

    if (this.pagedGroups.length < 1) {
      this.pager.pages.pop();
      this.pager.currentPage > this.pager.pages.length ?
        this.setPage(this.pager.currentPage - 1) :
        this.setPage(this.pager.currentPage);
    }
  }

  setPage(page: number) {
    this.pager = this.pagerService.createPager(this.searchedGroups.length, page, 6);
    this.pagedGroups = this.groups
      .filter(group => this.searchedGroups.find(gr => gr.id === group.id))
      .slice(this.pager.startIndex, this.pager.endIndex + 1)
      .reverse();
  }

  handleError(error: any) {
    this.router.navigateByUrl('/error');
  }

  sortGroupsByName() {
    this.sortService.sortGroupsByName(this.groups);
    this.setPage(this.pager.currentPage);
  }
}
