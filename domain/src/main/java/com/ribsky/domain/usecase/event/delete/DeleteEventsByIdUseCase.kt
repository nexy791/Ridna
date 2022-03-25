package com.ribsky.domain.usecase.event.delete

import com.ribsky.domain.repository.EventRepository

interface DeleteEventsByIdUseCase {

    suspend fun invoke(id: String): Boolean
}

class DeleteEventsByIdUseCaseImpl(
    private val eventRepository: EventRepository
) : DeleteEventsByIdUseCase {
    override suspend fun invoke(id: String): Boolean = eventRepository.deleteEvents(id)
}
