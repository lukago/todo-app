import {Injectable} from '@angular/core';
import {Http} from '@angular/http';

import 'rxjs/add/operator/map';
import {Observable} from 'rxjs/Observable';
import {Group} from '../models/Group';
import {Task} from '../models/task';
import {GroupService} from './group.service';

@Injectable()
export class SearchService {
  private groupsUrl = 'api/groups';
  private tasksUrl = 'api/tasks';

  constructor(private http: Http) {
  }

  searchTasks(term: string, tasks: Task[]): Observable<Task[]> {
    if (!term) {
      return Observable.of(tasks);
    }

    return Observable.of(tasks.filter(t => t.title.indexOf(term) !== -1));
  }

  searchGroups(term: string, groups: Group[]): Observable<Group[]> {
    if (!term) {
      return this.http.get(this.groupsUrl)
        .map(resposne => resposne.json().groups);
    }

    return Observable.of(groups.filter(g => g.title.indexOf(term) !== -1));
  }

  searchTasksFromDb(term: string): Observable<Task[]> {
    let url = term ? `${this.tasksUrl}/?title=${term}` : this.tasksUrl;

    return this.http
      .get(url)
      .map(resposne => GroupService.toTaskArray(resposne.json()));
  }

  searchGroupsFromDb(term: string): Observable<Group[]> {
    let url = term ? `${this.groupsUrl}/?title=${term}` : this.groupsUrl;

    return this.http
      .get(url)
      .map(resposne => resposne.json().groups);
  }

}
