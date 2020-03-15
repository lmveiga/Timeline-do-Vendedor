package com.gmail.lucasmveigabr.timelinedovendedor.core

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gmail.lucasmveigabr.timelinedovendedor.util.SingleLiveEvent

sealed class NavigationEvent {
    object TimelineNavigation : NavigationEvent()
    object AddTaskNavigation : NavigationEvent()
}

class NavigationViewModel : ViewModel() {

    private val _navigation = SingleLiveEvent<NavigationEvent>()
    val navigation: LiveData<NavigationEvent> = _navigation

    fun setNavigation(event: NavigationEvent) {
        _navigation.postValue(event)
    }

}