package com.ribsky.domain.usecase.event.delete

import com.ribsky.domain.repository.EventRepository

interface DeleteEventByIdUseCase {

    suspend fun invoke(id: Int): Boolean
}

class DeleteEventByIdUseCaseImpl(
    private val eventRepository: EventRepository
) : DeleteEventByIdUseCase {
    override suspend fun invoke(id: Int): Boolean = eventRepository.deleteEvent(id)
}
