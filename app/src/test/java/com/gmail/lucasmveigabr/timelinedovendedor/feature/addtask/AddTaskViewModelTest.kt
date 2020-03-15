package com.gmail.lucasmveigabr.timelinedovendedor.feature.addtask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType
import com.gmail.lucasmveigabr.timelinedovendedor.data.networking.TasksFirebase
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class AddTaskViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    lateinit var sut: AddTaskViewModel
    lateinit var firebase: TasksFirebase

    var stateHandle = SavedStateHandle()

    @Before
    fun setUp() {
        firebase = mockk()
        sut = AddTaskViewModel(stateHandle, firebase)
    }

    @Test
    fun `when setDate is called should update date livedata`() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.YEAR, 2000)
        sut.setDate(Date(calendar.timeInMillis))
        assertEquals(calendar.timeInMillis, sut.taskDate.value!!.time)
    }

    @Test
    fun `when date is not set should call correct error`() {
        stateHandle["taskDate"] = null
        sut.createTaskButtonClick(TaskType.CALL, "customer", "description")
        assertEquals(AddTaskError.EMPTY_DATE, sut.errorMessage.value)
    }

    @Test
    fun `when customer is blank should call correct error`() {
        sut.createTaskButtonClick(TaskType.CALL, "", "description")
        assertEquals(AddTaskError.EMPTY_CUSTOMER, sut.errorMessage.value)
    }

    @Test
    fun `when description is blank should call correct error`() {
        sut.createTaskButtonClick(TaskType.CALL, "customer", "  ")
        assertEquals(AddTaskError.EMPTY_DESCRIPTION, sut.errorMessage.value)
    }

    @Test
    fun `when firebase returns error should call correct error`() {
        firebaseError()
        sut.createTaskButtonClick(TaskType.CALL, "customer", "description")
        assertEquals(AddTaskError.FIREBASE_ERROR, sut.errorMessage.value)
    }

    @Test
    fun `when firebase returns success should call insertion event`() {
        var isCalled = false
        sut.insertedAction.observeForever { isCalled = true }
        firebaseSuccess()
        sut.createTaskButtonClick(TaskType.CALL, "customer", "description")
        assertTrue(isCalled)
    }

    private fun firebaseSuccess() {
        every {
            firebase.addTask(any())
        }.answers {
            val data = MutableLiveData<Boolean>()
            data.postValue(true)
            return@answers data
        }
    }

    private fun firebaseError() {
        every {
            firebase.addTask(any())
        }.answers {
            val data = MutableLiveData<Boolean>()
            data.postValue(false)
            return@answers data
        }
    }
}