package com.gmail.lucasmveigabr.timelinedovendedor.data.networking

import androidx.lifecycle.LiveData
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Result
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task

interface TasksFirebase {

    fun listenToWeeklyTasks(): LiveData<Result<List<Task>>>

    fun addTask(task: Task): LiveData<Boolean>

    fun isLastDateUpdated(): Boolean
}