package com.ribsky.domain.usecase.event

import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.domain.repository.EventRepository

interface GetAllEventsUseCase {

    suspend fun invoke(): List<BaseEventModel>
}

class GetAllEventsUseCaseImpl(
    private val eventRepository: EventRepository
) : GetAllEventsUseCase {

    override suspend fun invoke(): List<BaseEventModel> = eventRepository.getAllEvents()
}
