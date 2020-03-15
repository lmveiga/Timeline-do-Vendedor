package com.gmail.lucasmveigabr.timelinedovendedor.data.model

import java.util.*

enum class TaskType {
    MAIL, CALL, PROPOSAL, MEETING, VISIT, OTHER;

    companion object {
        private val values = values()
        fun fromInt(value: Int?) = values.firstOrNull { it.ordinal == value } ?: OTHER
    }
}

data class Task(
    val type: TaskType,
    val description: String,
    val customer: String,
    val date: Date
)