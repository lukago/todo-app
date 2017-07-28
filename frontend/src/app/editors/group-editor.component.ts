import {Location} from '@angular/common';
import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

import 'rxjs/add/operator/switchMap';
import {Group} from '../models/group';
import {Task} from '../models/task';
import {GroupService} from '../services/group.service';

@Component({
  selector: 'group-editor',
  templateUrl: './group-editor.component.html'
})
export class GroupEditorComponent implements OnInit {
  group: Group;
  tasks: Task[] = [];
  showWrongInputMsg = false;
  loadedTasks = false;

  constructor(private groupService: GroupService,
              private route: ActivatedRoute,
              private location: Location,
              private router: Router) {
  }

  ngOnInit(): void {
    this.route.paramMap
      .switchMap((params: ParamMap) =>
        this.groupService
          .getGroup(+params.get('id'))
          .catch(e => this.handleError(e)))
      .subscribe((group: Group) => {
        this.group = group;
        if (!this.group) {
          return;
        }
        this.groupService.getTasksByGroupId(this.group.id)
          .then(tasks => this.tasks = tasks)
          .then(() => this.loadedTasks = true)
          .catch(e => this.handleError(e));
      });
  }

  handleError(error: any) {
    this.router.navigateByUrl('/error');
  }

  goBack(): void {
    this.location.back();
  }

  saveGroupChanges(): void {
    if (this.group.title.length < 1) {
      this.showWrongInputMsg = true;
      return;
    }

    this.showWrongInputMsg = false;
    this.groupService.updateGroup(this.group)
      .then(() => this.goBack())
      .catch(e => this.handleError(e));
  }
}
