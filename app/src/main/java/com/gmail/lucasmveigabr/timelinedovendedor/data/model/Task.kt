package com.gmail.lucasmveigabr.timelinedovendedor.data.model

import java.util.*

enum class TaskType {
    MAIL, CALL, PROPOSAL, MEETING, VISIT, OTHER
}

data class Task(
    val type: TaskType,
    val description: String,
    val customer: String,
    val date: Date
)