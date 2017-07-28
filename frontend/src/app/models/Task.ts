export class Task {
  id: number;
  title: string;
  startDate: Date;
  note: string;
  priority: number;
  groupId: number;
  status: boolean;

  constructor(id: number, title: string, startDate: Date,
              note: string, priority: number, groupId: number,
              status: boolean) {
    this.id = id;
    this.title = title;
    this.startDate = new Date(startDate);
    this.note = note;
    this.priority = priority;
    this.groupId = groupId;
    this.status = status;
  }
}
