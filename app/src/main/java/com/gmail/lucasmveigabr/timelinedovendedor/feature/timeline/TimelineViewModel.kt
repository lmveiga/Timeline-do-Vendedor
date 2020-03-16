package com.gmail.lucasmveigabr.timelinedovendedor.feature.timeline

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Result
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType
import com.gmail.lucasmveigabr.timelinedovendedor.data.networking.TasksFirebase
import com.gmail.lucasmveigabr.timelinedovendedor.util.SingleLiveEvent
import java.util.*

class TimelineViewModel(private val tasksFirebase: TasksFirebase) : ViewModel() {

    private val _timelineData = MutableLiveData<List<Task>>()
    val timelineData: LiveData<List<Task>> = _timelineData

    private val _firebaseError = SingleLiveEvent<Unit>()
    val firebaseError: LiveData<Unit> = _firebaseError

    val timelineCountData = Transformations.map(_timelineData) { tasks ->
        val result = ArrayList<TimelineCountItem>()
        for (type in TaskType.values()) {
            result.add(TimelineCountItem(type, tasks.count { it.type == type }))
        }
        return@map result
    }

    private var firebaseLiveData = tasksFirebase.listenToWeeklyTasks()

    private val firebaseObserver = Observer<Result<List<Task>>> {
        when (it) {
            is Result.Success -> _timelineData.postValue(it.data)
            is Result.Failure -> {
                _firebaseError.call()
            }
        }
    }

    init {
        firebaseLiveData.observeForever(firebaseObserver)
    }

    fun fragmentStart() {
        if (!tasksFirebase.isLastDateUpdated()) {
            firebaseLiveData.removeObserver(firebaseObserver)
            firebaseLiveData = tasksFirebase.listenToWeeklyTasks()
            firebaseLiveData.observeForever(firebaseObserver)
        }
    }

    override fun onCleared() {
        super.onCleared()
        firebaseLiveData.removeObserver(firebaseObserver)
    }


}
