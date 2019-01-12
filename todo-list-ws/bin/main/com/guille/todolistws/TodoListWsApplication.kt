package com.guille.todolistws

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodoListWsApplication

fun main(args: Array<String>) {
	runApplication<TodoListWsApplication>(*args)
}

