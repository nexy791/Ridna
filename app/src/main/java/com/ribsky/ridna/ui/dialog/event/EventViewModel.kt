package com.ribsky.ridna.ui.dialog.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.data.model.event.EventApiModel
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.domain.usecase.event.DeleteEventUseCase
import com.ribsky.domain.usecase.event.GetEventByIdUseCase
import com.ribsky.domain.usecase.event.GetEventsByIdUseCase
import com.ribsky.ridna.utils.Event
import kotlinx.coroutines.launch

class EventViewModel(
    private val getEventByIdUseCase: GetEventByIdUseCase,
    private val getEventsByIdUseCase: GetEventsByIdUseCase,
    private val deleteEventUseCase: DeleteEventUseCase
) : ViewModel() {

    private val _statusEventInfo: MutableLiveData<BaseEventModel?> = MutableLiveData()
    val statusEventInfo: LiveData<BaseEventModel?> get() = _statusEventInfo

    private val _statusEvents: MutableLiveData<List<BaseEventModel?>> = MutableLiveData()
    val statusEvents: LiveData<List<BaseEventModel?>> get() = _statusEvents

    private val _statusDeleteEvent: Event<Boolean> = Event()
    val statusDeleteEvent: LiveData<Boolean> get() = _statusDeleteEvent

    fun getEvent(id: Int) {
        viewModelScope.launch {
            val event = getEventByIdUseCase.invoke(id) as EventApiModel
            _statusEventInfo.value = event
            getAllEventsById(event.id)
        }
    }

    private fun getAllEventsById(id: String) {
        viewModelScope.launch {
            _statusEvents.value = getEventsByIdUseCase.invoke(id)
        }
    }

    fun deleteEvent(id: Int) {
        viewModelScope.launch {
            _statusDeleteEvent.value = deleteEventUseCase.deleteEvent(id)
        }
    }

    fun deleteAllEvent(id: String) {
        viewModelScope.launch {
            _statusDeleteEvent.value = deleteEventUseCase.deleteEvents(id)
        }
    }
}
