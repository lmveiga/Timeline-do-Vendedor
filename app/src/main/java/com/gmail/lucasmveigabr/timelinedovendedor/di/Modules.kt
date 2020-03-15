package com.gmail.lucasmveigabr.timelinedovendedor.di

import androidx.lifecycle.SavedStateHandle
import com.gmail.lucasmveigabr.timelinedovendedor.core.NavigationViewModel
import com.gmail.lucasmveigabr.timelinedovendedor.data.networking.TasksFirebase
import com.gmail.lucasmveigabr.timelinedovendedor.data.networking.TasksFirebaseImpl
import com.gmail.lucasmveigabr.timelinedovendedor.feature.addtask.AddTaskViewModel
import com.gmail.lucasmveigabr.timelinedovendedor.feature.timeline.TimelineViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val firebaseModule = module {
    single<TasksFirebase> { TasksFirebaseImpl() }
}

val viewModelModule = module {
    viewModel { (handle: SavedStateHandle) ->
        AddTaskViewModel(handle, get())
    }
    viewModel {
        TimelineViewModel(get())
    }
    viewModel {
        NavigationViewModel()
    }
}