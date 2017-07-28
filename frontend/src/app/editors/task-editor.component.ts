import {DatePipe, Location} from '@angular/common';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

import 'rxjs/add/operator/switchMap';
import {Group} from '../models/group';
import {Task} from '../models/task';
import {GroupService} from '../services/group.service';

@Component({
  selector: 'task-editor',
  templateUrl: './task-editor.component.html',
  providers: [DatePipe]
})
export class TaskEditorComponent implements OnInit {
  task: Task;
  group: Group;
  dateStr: string;
  groups: Group[] = [];
  showWrongInputMsg = false;

  constructor(private groupService: GroupService,
              private route: ActivatedRoute,
              private location: Location,
              private datePipe: DatePipe,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.paramMap
      .switchMap((params: ParamMap) =>
        this.groupService
          .getTask(+params.get('id'))
          .catch(e => this.handleError(e)))
      .subscribe((task: Task) => {
        this.task = task;
        this.dateStr = this.datePipe.transform(this.task.startDate, 'yyyy-MM-ddTHH:mm');
        this.groupService.getGroups()
          .then(groups => this.groups = groups)
          .then(() => this.group = this.groups.find(gr => gr.id === task.id))
          .catch(e => this.handleError(e));
      });
  }

  goBack(): void {
    this.location.back();
  }

  handleError(error: any) {
    this.router.navigateByUrl('/error');
  }

  saveTaskChanges(): void {

    if (this.task.title.length < 1 || this.task.priority == null
      || this.task.startDate == null || this.task.groupId == null) {
      this.showWrongInputMsg = true;
      return;
    }

    this.showWrongInputMsg = false;
    this.task.startDate = new Date(this.dateStr);
    this.groupService.updateTask(this.task)
      .then(() => this.goBack())
      .catch(() => this.router.navigateByUrl('/error'));
  }
}
