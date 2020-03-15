package com.gmail.lucasmveigabr.timelinedovendedor.feature.timeline

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Result
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType
import com.gmail.lucasmveigabr.timelinedovendedor.data.networking.TasksFirebase
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*


class TimelineViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var sut: TimelineViewModel
    private lateinit var firebase: TasksFirebase
    private val data = MutableLiveData<Result<List<Task>>>()

    @Before
    fun setUp() {
        firebase = mockk()
        success()
        sut = TimelineViewModel(firebase)
        sut.timelineCountData.observeForever { } //needed for tests
    }

    @Test
    fun `when viewmodel is initialized and firebase fetch is successful, should update livedata`() {
        assertNotNull(sut.timelineData.value)
        assertEquals(2, sut.timelineData.value!!.size)
    }

    @Test
    fun `when firebase updates data, livedata should update as well`() {
        data.postValue(Result.Success(listOf(Task(TaskType.CALL, "desc", "Customer", Date()))))
        assertEquals(1, sut.timelineData.value!!.size)
    }

    @Test
    fun `when livedata is updated, count livedata should be updated as well`() {
        assertNotNull(sut.timelineCountData.value!!.size)
        assertEquals(0, sut.timelineCountData.value!!.first { it.type == TaskType.CALL }.count)
        assertEquals(1, sut.timelineCountData.value!!.first { it.type == TaskType.VISIT }.count)
    }

    @Test
    fun `when firebase returns error, error event should be called`() {
        var isCalled = false
        sut.firebaseError.observeForever { isCalled = true }
        data.value = Result.Failure(Exception())
        assertTrue(isCalled)
    }

    @Test
    fun `when viewmodel is cleared should clear firebase observer`() {
        //todo
    }

    private fun success() {
        every { firebase.listenToWeeklyTasks() }.answers {
            data.postValue(
                Result.Success(
                    listOf(
                        Task(TaskType.MEETING, "description", "customer", Date()),
                        Task(TaskType.VISIT, "description", "customer", Date())
                    )
                )
            )
            return@answers data
        }
    }
}