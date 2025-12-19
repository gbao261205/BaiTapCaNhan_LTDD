package com.vibecoding.baitap11.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.vibecoding.baitap11.data.DatabaseHelper
import com.vibecoding.baitap11.model.Task

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val dbHelper = DatabaseHelper(application)
    private var currentUserId: Int = -1

    val tasks = MutableLiveData<List<Task>>()
    val newTaskTitle = MutableLiveData<String>()
    val newTaskDescription = MutableLiveData<String>()
    val message = MutableLiveData<String>()

    fun init(userId: Int) {
        currentUserId = userId
        loadTasks()
    }

    private fun loadTasks() {
        if (currentUserId != -1) {
            tasks.value = dbHelper.getAllTasks(currentUserId)
        }
    }

    fun onAddTaskClicked() {
        val title = newTaskTitle.value
        val desc = newTaskDescription.value ?: ""

        if (title.isNullOrEmpty()) {
            message.value = "Please enter task title"
            return
        }

        if (currentUserId == -1) {
            message.value = "User not logged in"
            return
        }

        val task = Task(title = title, description = desc, userId = currentUserId)
        val result = dbHelper.addTask(task)

        if (result > -1) {
            newTaskTitle.value = ""
            newTaskDescription.value = ""
            loadTasks()
            message.value = "Task added"
        } else {
            message.value = "Failed to add task"
        }
    }

    fun onTaskChecked(task: Task, isChecked: Boolean) {
        val updatedTask = task.copy(isCompleted = isChecked)
        dbHelper.updateTask(updatedTask)
        loadTasks()
    }

    fun onDeleteTask(task: Task) {
        dbHelper.deleteTask(task.id)
        loadTasks()
        message.value = "Task deleted"
    }
}