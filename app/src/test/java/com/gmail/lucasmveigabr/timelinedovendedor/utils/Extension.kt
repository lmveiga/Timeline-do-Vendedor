package com.gmail.lucasmveigabr.timelinedovendedor.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore

fun ViewModel.callOnCleared() {
    val viewModelStore = ViewModelStore()
    val viewModelProvider = ViewModelProvider(viewModelStore, object : ViewModelProvider.Factory {

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T = this@callOnCleared as T
    })
    viewModelProvider.get(this@callOnCleared::class.java)

    //Run 2
    viewModelStore.clear()//To call clear() in ViewModel
}