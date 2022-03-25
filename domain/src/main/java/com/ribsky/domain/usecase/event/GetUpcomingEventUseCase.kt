package com.ribsky.domain.usecase.event

import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.domain.repository.EventRepository

interface GetUpcomingEventUseCase {

    suspend fun invoke(): BaseEventModel?
}

class GetUpcomingEventUseCaseImpl(
    private val eventRepository: EventRepository
) : GetUpcomingEventUseCase {

    override suspend fun invoke(): BaseEventModel? = eventRepository.getUpcomingEvent()
}
