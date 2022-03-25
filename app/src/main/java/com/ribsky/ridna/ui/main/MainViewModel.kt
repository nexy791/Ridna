package com.ribsky.ridna.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ribsky.data.model.event.EventApiModel
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.domain.usecase.event.GetEventsByDay
import com.ribsky.domain.usecase.event.GetEventsByDays
import com.ribsky.domain.usecase.event.GetUpcomingEventUseCase
import com.ribsky.ridna.model.calendar.DayModel
import com.ribsky.ridna.utils.Event
import kotlinx.coroutines.launch

class MainViewModel(
    private val getUpcomingEventUseCase: GetUpcomingEventUseCase,
    private val getEventsByDays: GetEventsByDays,
    private val getEventsByDay: GetEventsByDay
) : ViewModel() {

    private val _statusUpcomingEvent: MutableLiveData<BaseEventModel?> = MutableLiveData()
    val statusUpcomingEvent: LiveData<BaseEventModel?> get() = _statusUpcomingEvent

    private val _statusCalendarEvents: MutableLiveData<List<DayModel>> = MutableLiveData()
    val statusCalendarEvents: LiveData<List<DayModel>> get() = _statusCalendarEvents

    private val _statusDayEvents: MutableLiveData<List<BaseEventModel>> = MutableLiveData()
    val statusDayEvents: LiveData<List<BaseEventModel>> get() = _statusDayEvents

    private val _statusUpcomingEvents: Event<List<BaseEventModel>> = Event()
    val statusUpcomingEvents: LiveData<List<BaseEventModel>> get() = _statusUpcomingEvents

    private var dates: LongRange? = null
    private var pickedDate: Long? = null

    fun updateInfo() {
        getUpcomingEvent()
        dates?.let { getCalendarEvents(it) }
        pickedDate?.let { getEventByDate(it) }
    }

    fun getCalendarEvents(dates: LongRange) {

        this.dates = dates

        viewModelScope.launch {
            val result = getEventsByDays.invoke(dates)
            _statusCalendarEvents.value = result.map { event ->
                if (event is EventApiModel) {
                    DayModel(
                        date = event.date,
                        color = event.color
                    )
                } else {
                    error("Unexpected type")
                }
            }
        }
    }

    fun getEventByDate(date: Long) {
        pickedDate = date
        viewModelScope.launch {
            _statusDayEvents.value = getEventsByDay.invoke(date)
        }
    }

    fun getUpcomingEvents(date: Long) {
        viewModelScope.launch {
            _statusUpcomingEvents.value = getEventsByDay.invoke(date).distinctBy { event ->
                (event as EventApiModel).id
            }
        }
    }

    fun getUpcomingEvent() {
        viewModelScope.launch {
            _statusUpcomingEvent.value = getUpcomingEventUseCase.invoke()
        }
    }
}
