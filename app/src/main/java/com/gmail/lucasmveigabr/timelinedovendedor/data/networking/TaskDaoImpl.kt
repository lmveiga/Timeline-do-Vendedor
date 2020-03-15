package com.gmail.lucasmveigabr.timelinedovendedor.data.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Result
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.Task
import com.gmail.lucasmveigabr.timelinedovendedor.data.model.TaskType
import com.google.firebase.firestore.FirebaseFirestore

class TaskDaoImpl : TaskDao {

    override fun listenToTasks(): LiveData<Result<List<Task>>> {
        val data = MutableLiveData<Result<List<Task>>>()
        val db = FirebaseFirestore.getInstance()
        db.collection("tasks")
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->  //TODO: FILTER DATE
                if (firebaseFirestoreException != null) {
                    data.postValue(Result.Failure(firebaseFirestoreException))
                } else {
                    if (querySnapshot != null) {
                        val list = ArrayList<Task>()
                        for (task in querySnapshot.documents) {
                            list.add(
                                Task(
                                    TaskType.fromInt(task.getLong("type")?.toInt()),
                                    task["description"].toString(),
                                    task["customer"].toString(),
                                    task.getDate("date")!!  //todo: value could be null
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

}