package com.ribsky.domain.usecase.event

import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.domain.repository.EventRepository

interface CreateEventUseCase {

    suspend fun invoke(eventModel: BaseEventModel, addNotifications: Boolean = true): Boolean
}

class CreateEventUseCaseImpl(
    private val eventRepository: EventRepository
) : CreateEventUseCase {

    override suspend fun invoke(eventModel: BaseEventModel, addNotifications: Boolean): Boolean =
        eventRepository.createEvent(eventModel, addNotifications)
}
