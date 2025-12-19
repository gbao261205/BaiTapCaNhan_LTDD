package com.vibecoding.baitap11.model

data class Task(
    val id: Int = -1,
    val title: String,
    val description: String,
    val isCompleted: Boolean = false,
    val userId: Int // To link task to a user
)