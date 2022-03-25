package com.ribsky.domain.usecase.delete

import com.ribsky.domain.repository.EventRepository
import com.ribsky.domain.repository.RelationRepository

interface DeleteAllUseCase {

    suspend fun invoke(): Boolean
}

class DeleteAllUseCaseImpl(
    private val eventRepository: EventRepository,
    private val relationRepository: RelationRepository
) : DeleteAllUseCase {

    override suspend fun invoke(): Boolean {
        val eventResult = eventRepository.deleteEvents()
        val relationResult = relationRepository.deleteRelation()
        return eventResult && relationResult
    }
}
