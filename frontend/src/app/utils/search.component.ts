import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import 'rxjs/add/observable/of';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/debounceTime';
import 'rxjs/add/operator/distinctUntilChanged';
import {Observable} from 'rxjs/Observable';
import {Subject} from 'rxjs/Subject';
import {Group} from '../models/Group';
import {Task} from '../models/task';
import {SearchService} from '../services/search.service';

@Component({
  selector: 'tmgr-search',
  templateUrl: './search.component.html',
  providers: [SearchService]
})
export class SearchComponent implements OnInit {
  @Output() searchedTasks = new EventEmitter<Observable<Task[]>>();
  @Output() searchedGroups = new EventEmitter<Observable<Group[]>>();
  @Input() tasksAll: Task[];
  @Input() groupsAll: Group[];
  @Input() useDb = false;

  private tasks = Observable.of<Task[]>([]);
  private groups = Observable.of<Group[]>([]);
  private taskSearchTerms = new Subject<string>();
  private groupSearchTerms = new Subject<string>();

  constructor(private searchService: SearchService) {
  }

  searchTasks(term: string): void {
    this.taskSearchTerms.next(term);
    this.searchedTasks.emit(this.tasks);
  }

  searchGroups(term: string): void {
    this.groupSearchTerms.next(term);
    this.searchedGroups.emit(this.groups);
  }

  ngOnInit(): void {
    this.tasks = this.taskSearchTerms
      .debounceTime(300)
      .distinctUntilChanged()
      .switchMap(term => {
        if (this.useDb) {
          return this.searchService.searchTasksFromDb(term);
        } else {
          return this.searchService.searchTasks(term, this.tasksAll);
        }
      })
      .catch(error => {
        console.log(error);
        return Observable.of<Task[]>([]);
      });

    this.groups = this.groupSearchTerms
      .debounceTime(300)
      .distinctUntilChanged()
      .switchMap(term => {
        if (this.useDb) {
          return this.searchService.searchGroupsFromDb(term);
        } else {
          return this.searchService.searchGroups(term, this.groupsAll);
        }
      })
      .catch(error => {
        console.log(error);
        return Observable.of<Group[]>([]);
      });
  }
}
