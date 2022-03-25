package com.ribsky.ridna.ui.events

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.domain.usecase.event.GetAllEventsUseCase
import kotlinx.coroutines.launch

class EventsViewModel(
    private val getAllEventsUseCase: GetAllEventsUseCase
) : ViewModel() {

    private val _eventsStatus: MutableLiveData<List<BaseEventModel>> = MutableLiveData()
    val eventsStatus: LiveData<List<BaseEventModel>> get() = _eventsStatus

    fun loadEvents() {
        viewModelScope.launch {
            _eventsStatus.value = getAllEventsUseCase.invoke()
        }
    }
}
