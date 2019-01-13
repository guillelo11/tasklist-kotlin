import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TaskListComponent } from './task-list/task-list.component';

const routes: Routes = [{ path: '', redirectTo: '/tasks', pathMatch: 'full' },
{ path: 'tasks', component: TaskListComponent }];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
