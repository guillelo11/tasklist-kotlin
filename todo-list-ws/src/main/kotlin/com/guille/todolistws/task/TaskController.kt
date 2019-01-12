package com.guille.todolistws.task

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/api/task")
class TaskController(val taskService: TaskService) {

    @GetMapping
    fun getTasks() = taskService.getTasks()

    @GetMapping("/{id}")
    fun getTask(@PathVariable id: Long): ResponseEntity<Task> {
        val task = taskService.getTask(id)
        return if(task.isPresent) {
            ResponseEntity.ok(task.get())
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @PostMapping
    fun saveTask(@Valid @RequestBody task: Task): ResponseEntity<Task> {
        return if(!taskService.getTask(task.id).isPresent) {
            ResponseEntity.status(HttpStatus.CREATED).body(taskService.saveTask(task))
        } else {
            ResponseEntity.status(HttpStatus.CONFLICT).build()
        }
    }

    @PutMapping("/{id}")
    fun updateTask(@PathVariable id: Long, @Valid @RequestBody task: Task): ResponseEntity<Task> {
        val t = taskService.getTask(id)
        return if (t.isPresent) {
            ResponseEntity.ok(taskService.updateTask(id, task))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteTask(@PathVariable id: Long): ResponseEntity<Void> {
        return taskService.getTask(id).map {
            task -> taskService.deleteTask(id)
            ResponseEntity<Void>(HttpStatus.OK)
        }.orElse(ResponseEntity.notFound().build())
    }
}