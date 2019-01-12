package com.guille.todolistws.task

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
data class Task (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long,
        @get:NotBlank val description: String)