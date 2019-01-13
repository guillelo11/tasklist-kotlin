import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Task } from './task';

const API_URL = environment.api_url;

@Injectable({
  providedIn: 'root'
})
export class ApiService {

  constructor(private httpClient: HttpClient) { }

  public getTasks(): Observable<Task[]> {
    return this.httpClient.get<Task[]>(API_URL + '/api/task');
  }

  public getTask(id: String): Observable<Task> {
    return this.httpClient.get<Task>(API_URL + 'api/task/' + id);
  }

  public saveTask(task: Task): Observable<Task> {
    return this.httpClient.post<Task>(API_URL + '/api/task', task);
  }

  public deleteTask(id: String) {
    return this.httpClient.delete(API_URL + 'api/task/' + id);
  }

  public updateTask(task: Task): Observable<Task> {
    return this.httpClient.put<Task>(API_URL + 'api/task/' + task.id, task);
  }
}
