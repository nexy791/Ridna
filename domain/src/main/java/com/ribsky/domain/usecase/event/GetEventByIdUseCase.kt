package com.ribsky.domain.usecase.event

import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.domain.repository.EventRepository

interface GetEventByIdUseCase {

    suspend fun invoke(id: Int): BaseEventModel?
}

class GetEventByIdUseCaseImpl(
    private val eventRepository: EventRepository
) : GetEventByIdUseCase {

    override suspend fun invoke(id: Int): BaseEventModel? = eventRepository.getEvent(id)
}
