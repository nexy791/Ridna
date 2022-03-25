package com.ribsky.domain.usecase.event

import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.domain.repository.EventRepository

interface GetEventsByDays {

    suspend fun invoke(dates: LongRange): List<BaseEventModel>
}

class GetEventsByDaysImpl(
    private val eventRepository: EventRepository
) : GetEventsByDays {

    override suspend fun invoke(dates: LongRange): List<BaseEventModel> =
        eventRepository.getEventsByDates(dates)
}
