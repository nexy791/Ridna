package com.ribsky.domain.usecase.event

import com.ribsky.domain.usecase.event.delete.DeleteEventByIdUseCase
import com.ribsky.domain.usecase.event.delete.DeleteEventsByIdUseCase

interface DeleteEventUseCase {

    suspend fun deleteEvent(id: Int): Boolean
    suspend fun deleteEvents(id: String): Boolean
}

class DeleteEventUseCaseImpl(
    private val deleteEventByIdUseCase: DeleteEventByIdUseCase,
    private val deleteEventsByIdUseCase: DeleteEventsByIdUseCase
) : DeleteEventUseCase {
    override suspend fun deleteEvent(id: Int): Boolean = deleteEventByIdUseCase.invoke(id)

    override suspend fun deleteEvents(id: String): Boolean = deleteEventsByIdUseCase.invoke(id)
}
