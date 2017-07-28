import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {GroupEditorComponent} from '../editors/group-editor.component';
import {TaskEditorComponent} from '../editors/task-editor.component';
import {HomeComponent} from '../home/home.component';
import {ErrorComponent} from '../utils/error.component';

const routes: Routes = [
  {path: '', redirectTo: '/home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  {path: 'groups/:id', component: GroupEditorComponent},
  {path: 'tasks/:id', component: TaskEditorComponent},
  {path: 'error', component: ErrorComponent},
  {path: '**', redirectTo: '/home'}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
