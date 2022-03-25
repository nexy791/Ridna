package com.ribsky.domain.repository

import com.ribsky.domain.model.event.BaseEventModel

interface EventRepository {

    suspend fun getUpcomingEvent(): BaseEventModel?

    suspend fun getAllEventsWithId(id: String): List<BaseEventModel>

    suspend fun getEvent(date: Long): List<BaseEventModel>

    suspend fun getEvent(id: Int): BaseEventModel?

    suspend fun deleteEvent(id: Int): Boolean

    suspend fun deleteEvents(id: String): Boolean

    suspend fun getEventsByDates(dates: LongRange): List<BaseEventModel>

    suspend fun createEvent(eventModel: BaseEventModel, addNotifications: Boolean): Boolean

    suspend fun getAllEvents(): List<BaseEventModel>

    suspend fun deleteEvents(): Boolean
}
