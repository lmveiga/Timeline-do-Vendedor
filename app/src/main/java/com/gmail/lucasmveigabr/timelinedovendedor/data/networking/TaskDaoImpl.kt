package com.gmail.lucasmveigabr.timelinedovendedor.data.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task
import com.google.firebase.firestore.FirebaseFirestore

class TaskDaoImpl : TaskDao {

    override fun listenToTasks(): LiveData<Result<List<Task>>> {
        val data = MutableLiveData<Result<List<Task>>>()
        val db = FirebaseFirestore.getInstance()
        db.collection("tasks")
            .get()
            .addOnSuccessListener { result ->
                data.postValue(Result.success(result.toObjects(Task::class.java)))
            }
            .addOnFailureListener {
                data.postValue(Result.failure(it))
            }
        return data
    }

}