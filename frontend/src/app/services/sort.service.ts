import {Injectable} from '@angular/core';
import {Group} from '../models/group';
import {Task} from '../models/task';

@Injectable()
export class SortService {

  groupsByName = 0;
  tasksByTitle = 0;
  tasksByDate = 0;
  tasksByPrio = 0;
  tasksByNote = 0;
  tasksByStatus = 0;
  tasksByGroupName = 0;

  sortGroupsByName(groups: Group[]) {
    if (this.groupsByName > 0) {
      groups.sort((a, b) => a.title > b.title ? 1 : -1);
      this.setAlltoZeros();
      this.groupsByName = -1;
    } else {
      groups.sort((a, b) => a.title < b.title ? 1 : -1);
      this.setAlltoZeros();
      this.groupsByName = 1;
    }
  }

  sortTasksByTitle(tasks: Task[]) {
    if (this.tasksByTitle > 0) {
      tasks.sort((a, b) => a.title > b.title ? 1 : -1);
      this.setAlltoZeros();
      this.tasksByTitle = -1;
    } else {
      tasks.sort((a, b) => a.title < b.title ? 1 : -1);
      this.setAlltoZeros();
      this.tasksByTitle = 1;
    }
  }

  sortTasksByDate(tasks: Task[]) {
    if (this.tasksByDate > 0) {
      tasks.sort((a, b) => a.startDate.getTime() - b.startDate.getTime());
      this.setAlltoZeros();
      this.tasksByDate = -1;
    } else {
      tasks.sort((a, b) => b.startDate.getTime() - a.startDate.getTime());
      this.setAlltoZeros();
      this.tasksByDate = 1;
    }
  }

  sortTasksByPrio(tasks: Task[]) {
    if (this.tasksByPrio > 0) {
      tasks.sort((a, b) => a.priority - b.priority);
      this.setAlltoZeros();
      this.tasksByPrio = -1;
    } else {
      tasks.sort((a, b) => b.priority - a.priority);
      this.setAlltoZeros();
      this.tasksByPrio = 1;
    }
  }

  sortTasksByNote(tasks: Task[]) {
    if (this.tasksByNote > 0) {
      tasks.sort((a, b) => a.note > b.note ? 1 : -1);
      this.setAlltoZeros();
      this.tasksByNote = -1;
    } else {
      tasks.sort((a, b) => a.note < b.note ? 1 : -1);
      this.setAlltoZeros();
      this.tasksByNote = 1;
    }
  }

  sortTasksByStatus(tasks: Task[]) {
    if (this.tasksByStatus > 0) {
      tasks.sort((a, b) => a.status > b.status ? 1 : -1);
      this.setAlltoZeros();
      this.tasksByStatus = -1;
    } else {
      tasks.sort((a, b) => a.status < b.status ? 1 : -1);
      this.setAlltoZeros();
      this.tasksByPrio = 1;
    }
  }

  sortTasksByGroupName(tasks: Task[], groups: Group[]): Task[] {
    let sortedTasks: Task[] = [];

    if (this.tasksByGroupName > 0) {
      groups.sort((a, b) => a.title > b.title ? 1 : -1);
      this.setAlltoZeros();
      this.tasksByGroupName = -1;
    } else {
      groups.sort((a, b) => a.title < b.title ? 1 : -1);
      this.setAlltoZeros();
      this.tasksByGroupName = 1;
    }

    groups.forEach(gr => sortedTasks.push(...tasks.filter(t => t.groupId === gr.id)));

    return sortedTasks;
  }

  private setAlltoZeros() {
    this.groupsByName = 0;
    this.tasksByTitle = 0;
    this.tasksByDate = 0;
    this.tasksByPrio = 0;
    this.tasksByNote = 0;
    this.tasksByStatus = 0;
    this.tasksByGroupName = 0;
  }
}
