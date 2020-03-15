package com.gmail.lucasmveigabr.timelinedovendedor.data.networking

import androidx.lifecycle.LiveData
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task

interface TaskDao {

    fun listenToTasks(): LiveData<Result<List<Task>>>

}