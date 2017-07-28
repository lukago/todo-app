import {Component, OnInit} from '@angular/core';
import {GroupService} from '../services/group.service';

@Component({
  selector: 'todo-error',
  templateUrl: './error.component.html'
})
export class ErrorComponent implements OnInit {

  error: any;
  message: string;
  errorCode: number;

  ngOnInit(): void {
    if (GroupService.lastErr) {
      this.error = GroupService.lastErr.json();
      this.errorCode = this.error.status;
      this.message = this.error.error;
    }
  }
}
