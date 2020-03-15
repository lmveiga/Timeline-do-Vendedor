package com.gmail.lucasmveigabr.timelinedovendedor.feature.addtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType
import com.gmail.lucasmveigabr.timelinedovendedor.data.networking.TasksFirebaseImpl
import com.gmail.lucasmveigabr.timelinedovendedor.util.SingleLiveEvent
import java.util.*

enum class AddTaskError {
    EMPTY_DATE, EMPTY_CUSTOMER, EMPTY_DESCRIPTION, FIREBASE_ERROR
}

class AddTaskViewModel(private val state: SavedStateHandle) : ViewModel() {

    val taskDate: LiveData<Date> = state.getLiveData("taskDate")

    private val _errorMessage = SingleLiveEvent<AddTaskError>()
    val errorMessage: LiveData<AddTaskError> = _errorMessage

    private val _insertedAction = SingleLiveEvent<Unit>()
    val insertedAction: LiveData<Unit> = _insertedAction

    val dao = TasksFirebaseImpl()


    fun setDate(date: Date) {
        state["taskDate"] = date
    }

    fun createTaskButtonClick(type: TaskType, customer: String, description: String) {
        val date = taskDate.value
        if (date == null) {
            _errorMessage.postValue(AddTaskError.EMPTY_DATE)
            return
        }
        if (customer.isBlank()) {
            _errorMessage.postValue(AddTaskError.EMPTY_CUSTOMER)
        }
        if (description.isBlank()) {
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