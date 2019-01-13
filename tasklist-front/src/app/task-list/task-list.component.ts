import { Component, OnInit } from '@angular/core';
import { ApiService } from '../api.service';
import { Task } from '../task';
import { FormGroup, FormControl, Validators, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-task-list',
  templateUrl: './task-list.component.html',
  styleUrls: ['./task-list.component.css']
})
export class TaskListComponent implements OnInit {

  tasks: Task[] = [];

  form: FormGroup;

  constructor(private apiService: ApiService, private formBuilder: FormBuilder) { }

  ngOnInit() {
    this.getTasks();
    this.form =  this.formBuilder.group({
      description : new FormControl('', Validators.required)
    });
  }

  private getTasks() {
    this.apiService.getTasks().subscribe((tasks) => {
      this.tasks = tasks;
    });
  }

  updateStatus(task: Task) {
    task.done = !task.done;
    this.apiService.updateTask(task).subscribe(() => {
      this.getTasks();
    });
  }

  delete(id: String) {
    this.apiService.deleteTask(id).subscribe(() => {
      this.getTasks();
    });
  }

  submit() {
    const task: Task = {id: null, description: this.form.controls.description.value, done: false};
    this.apiService.saveTask(task).subscribe((t) => {
      this.tasks.push(t);
      this.form.reset();
    });
  }
}
