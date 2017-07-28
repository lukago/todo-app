import {NgModule} from '@angular/core';
import {FormsModule} from '@angular/forms';
import {HttpModule} from '@angular/http';
import {BrowserModule} from '@angular/platform-browser';
import {GroupEditorComponent} from '../editors/group-editor.component';
import {TaskEditorComponent} from '../editors/task-editor.component';
import {GroupsComponent} from '../home/groups.component';
import {HomeComponent} from '../home/home.component';
import {TasksComponent} from '../home/tasks.component';
import {GroupService} from '../services/group.service';
import {ErrorComponent} from '../utils/error.component';
import {SearchComponent} from '../utils/search.component';
import {AppRoutingModule} from './app-routing.module';

import {AppComponent} from './app.component';

@NgModule({
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpModule,
    FormsModule,
  ],
  declarations: [
    AppComponent,
    HomeComponent,
    GroupsComponent,
    TasksComponent,
    GroupEditorComponent,
    TaskEditorComponent,
    SearchComponent,
    ErrorComponent
  ],
  providers: [GroupService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
