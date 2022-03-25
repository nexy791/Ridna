package com.ribsky.ridna.ui.dialog.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.domain.usecase.event.CreateEventUseCase
import com.ribsky.ridna.model.event.EventModel
import com.ribsky.ridna.utils.Event
import kotlinx.coroutines.launch

class AddEventViewModel(
    private val createEventUseCase: CreateEventUseCase
) : ViewModel() {

    private val _statusNewEvent: Event<Boolean> = Event()
    val statusNewEvent: LiveData<Boolean> get() = _statusNewEvent

    fun createNewEvent(eventModel: EventModel) {
        viewModelScope.launch {
            _statusNewEvent.value =
                createEventUseCase.invoke(eventModel, eventModel.addNotifications)
        }
    }
}
