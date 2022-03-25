package com.ribsky.domain.usecase.event

import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.domain.repository.EventRepository

interface GetEventsByIdUseCase {

    suspend fun invoke(id: String): List<BaseEventModel>
}

class GetEventsByIdUseCaseImpl(
    private val eventRepository: EventRepository
) : GetEventsByIdUseCase {

    override suspend fun invoke(id: String): List<BaseEventModel> =
        eventRepository.getAllEventsWithId(id)
}
