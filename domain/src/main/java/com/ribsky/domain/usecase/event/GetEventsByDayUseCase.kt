package com.ribsky.domain.usecase.event

import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.domain.repository.EventRepository

interface GetEventsByDay {

    suspend fun invoke(date: Long): List<BaseEventModel>
}

class GetEventsByDayImpl(
    private val eventRepository: EventRepository
) : GetEventsByDay {

    override suspend fun invoke(date: Long): List<BaseEventModel> = eventRepository.getEvent(date)
}
