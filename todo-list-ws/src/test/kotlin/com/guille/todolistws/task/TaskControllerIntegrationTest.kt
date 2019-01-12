package com.guille.todolistws.task

import org.hamcrest.Matchers.`is`
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.http.MediaType
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import java.io.IOException



@RunWith(SpringRunner::class)
@AutoConfigureMockMvc
@SpringBootTest
class TaskControllerIntegrationTest {

    @Autowired
    lateinit var mockMvc: MockMvc
    @Autowired
    lateinit var taskService: TaskService

    @Throws(IOException::class)
    fun convertObjectToJsonBytes(obj: Any): ByteArray {
        val mapper = ObjectMapper()
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        return mapper.writeValueAsBytes(obj)
    }

    @Test
    fun testFindTasks() {
        val tasks = taskService.getTasks()
        this.mockMvc.perform(get("/api/task")).andExpect(status().isOk).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.length()", `is`(tasks.size)))
    }

    @Test
    fun testFindTask() {
        val task = taskService.getTask(1).get()
        this.mockMvc.perform(get("/api/task/1")).andExpect(status().isOk).andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id", `is`(task.id.toInt())))
    }

    @Test
    fun testFindTaskNotFound() {
        this.mockMvc.perform(get("/api/task/20")).andExpect(status().isNotFound)
    }

    @Test
    fun testSaveTask() {
        val task = Task(3,"Make pizza")
        this.mockMvc.perform(post("/api/task").contentType(MediaType.APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(task)))
                .andExpect(status().isCreated).andExpect(jsonPath("$.id", `is`(task.id.toInt())))
    }

    @Test
    fun testSaveTaskNotValid() {
        val task = Task(3,"")
        this.mockMvc.perform(post("/api/task").contentType(MediaType.APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(task)))
                .andExpect(status().isBadRequest)
    }

    @Test
    fun testSaveTaskExisting() {
        val task = Task(1,"Buy pizza")
        this.mockMvc.perform(post("/api/task").contentType(MediaType.APPLICATION_JSON_UTF8).content(convertObjectToJsonBytes(task)))
                .andExpect(status().isConflict)
    }

    @Test
    fun testUpdateTask() {
        this.mockMvc.perform(put("/api/task/1").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"description\": \"buy beer\"}")).andExpect(status().isOk).andExpect(jsonPath("$.description", `is`("buy beer")))
    }

    @Test
    fun testUpdateTaskNotFound() {
        this.mockMvc.perform(put("/api/task/25").contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"description\": \"buy beer\"}")).andExpect(status().isNotFound)
    }

    @Test
    fun testDeleteTask() {
        this.mockMvc.perform(delete("/api/task/1")).andExpect(status().isOk)
    }

    @Test
    fun testDeleteTaskNotFound() {
        this.mockMvc.perform(delete("/api/task/50")).andExpect(status().isNotFound)
    }
}