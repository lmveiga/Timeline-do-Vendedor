package com.gmail.lucasmveigabr.timelinedovendedor.data.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Result
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList

class TasksFirebaseImpl : TasksFirebase {

    private var lastStartedDate: Date? = null

    override fun listenToWeeklyTasks(): LiveData<Result<List<Task>>> {
        val data = MutableLiveData<Result<List<Task>>>()
        val db = FirebaseFirestore.getInstance()
        lastStartedDate = getStartDate()
        db.collection("tasks")
            .whereGreaterThanOrEqualTo("date", getStartDate())
            .whereLessThan("date", getEndDate())
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (firebaseFirestoreException != null) {
                    data.postValue(Result.Failure(firebaseFirestoreException))
                } else {
                    if (querySnapshot != null) {
                        val list = ArrayList<Task>()
                        for (task in querySnapshot.documents) {
                            list.add(
                                Task(
                                    TaskType.valueOf(task["type"].toString()),
                                    task["description"].toString(),
                                    task["customer"].toString(),
                                    task.getDate("date")!!
                                )
                            )
                        }
                        data.postValue(Result.Success(list))
                    }
                }
            }
        return data
    }

    override fun addTask(task: Task): LiveData<Boolean> {
        val result = MutableLiveData<Boolean>()
        val db = FirebaseFirestore.getInstance()
        db.collection("tasks")
            .add(task)
            .addOnSuccessListener { result.postValue(true) }
            .addOnFailureListener { result.postValue(false) }
        return result
    }

    override fun isLastDateUpdated(): Boolean =
        getStartDate() == lastStartedDate

    private fun getStartDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        return Date(calendar.timeInMillis)
    }

    private fun getEndDate(): Date {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.clear(Calendar.MINUTE)
        calendar.clear(Calendar.SECOND)
        calendar.clear(Calendar.MILLISECOND)
        calendar.set(Calendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
        calendar.add(Calendar.DAY_OF_WEEK, 7)
        return Date(calendar.timeInMillis)
    }

}