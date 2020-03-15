package com.gmail.lucasmveigabr.timelinedovendedor.feature.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Result
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType
import com.gmail.lucasmveigabr.timelinedovendedor.data.networking.TasksFirebaseImpl
import java.util.*

class TimelineViewModel : ViewModel() {

    private val _timelineData = MutableLiveData<List<Task>>()
    val timelineData: LiveData<List<Task>> = _timelineData

    val timelineCountData = Transformations.map(_timelineData) { tasks ->
        val result = ArrayList<TimelineCountItem>()
        for (type in TaskType.values()) {
            result.add(TimelineCountItem(type, tasks.count { it.type == type }))
        }
        return@map result
    }

    init {
        val dao = TasksFirebaseImpl()
        dao.listenToWeeklyTasks().observeForever {
            when (it) {
                is Result.Success -> _timelineData.postValue(it.data)
                is Result.Failure -> {
                }
            }
        }
    }

}
