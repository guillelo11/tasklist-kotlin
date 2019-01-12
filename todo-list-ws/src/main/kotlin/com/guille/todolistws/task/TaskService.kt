package com.guille.todolistws.task

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
@Transactional
class TaskService(val taskRepository: TaskRepository) {

    fun getTasks() = taskRepository.findAll()

    fun getTask(id: Long): Optional<Task> {
        return taskRepository.findById(id)
    }

    fun saveTask(task: Task): Task {
        return taskRepository.save(task)
    }

    fun updateTask(id: Long, task: Task): Task {
        val t = taskRepository.findById(id).get()
        var updatedTask = t.copy(description = task.description)
        return taskRepository.save(updatedTask)
    }

    fun deleteTask(id: Long) {
        taskRepository.deleteById(id)
    }
}