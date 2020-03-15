package com.gmail.lucasmveigabr.timelinedovendedor.feature.addtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType
import com.gmail.lucasmveigabr.timelinedovendedor.data.networking.TaskDaoImpl
import com.gmail.lucasmveigabr.timelinedovendedor.util.SingleLiveEvent
import java.util.*

enum class AddTaskError {
    EMPTY_DATE, EMPTY_CUSTOMER, EMPTY_DESCRIPTION, FIREBASE_ERROR
}

class AddTaskViewModel : ViewModel() {

    private val _taskDate = MutableLiveData<Date>()
    val taskDate: LiveData<Date> = _taskDate

    private val _errorMessage = SingleLiveEvent<AddTaskError>()
    val errorMessage: LiveData<AddTaskError> = _errorMessage

    private val _insertedAction = SingleLiveEvent<Unit>()
    val insertedAction: LiveData<Unit> = _insertedAction

    val dao = TaskDaoImpl()


    fun setDate(date: Date) {
        _taskDate.postValue(date)
    }

    fun createTaskButtonClick(type: TaskType, customer: String, description: String) {
        val date = taskDate.value
        if (date == null) {
            _errorMessage.postValue(AddTaskError.EMPTY_DATE)
            return
        }
        if (customer.isNullOrBlank()) {
            _errorMessage.postValue(AddTaskError.EMPTY_CUSTOMER)
        }
        if (description.isNullOrBlank()) {
            _errorMessage.postValue(AddTaskError.EMPTY_DESCRIPTION)
        }
        val task = Task(type, description, customer, date)
        dao.addTask(task).observeForever { success ->
            if (success) {
                _insertedAction.call()
            } else {
                _errorMessage.postValue(AddTaskError.FIREBASE_ERROR)
            }
        }
    }


}