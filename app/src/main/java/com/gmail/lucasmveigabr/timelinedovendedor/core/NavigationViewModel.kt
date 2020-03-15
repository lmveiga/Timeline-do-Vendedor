package com.gmail.lucasmveigabr.timelinedovendedor.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

sealed class NavigationEvent {
    object timelineFragmentNavigation : NavigationEvent()
    object addTaskFragmentNavigation : NavigationEvent()
}

class NavigationViewModel : ViewModel() {

    private val _navigation = MutableLiveData<NavigationEvent>()
    val navigation: LiveData<NavigationEvent> = _navigation

    fun setNavigation(event: NavigationEvent) {
        _navigation.postValue(event)
    }

}