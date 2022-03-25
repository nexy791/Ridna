package com.ribsky.data.repository

import android.content.Context
import com.cesarferreira.tempo.*
import com.ribsky.data.R
import com.ribsky.data.db.event.EventDao
import com.ribsky.data.model.event.EventApiModel
import com.ribsky.data.utils.RandomColors
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.domain.repository.EventRepository
import java.util.*

class EventRepositoryImpl(
    private val eventDao: EventDao,
    private val context: Context
) : EventRepository {

    override suspend fun getUpcomingEvent(): BaseEventModel? =
        eventDao.getEvent((Tempo.now).time.date)

    override suspend fun getAllEventsWithId(id: String): List<BaseEventModel> =
        eventDao.getEvents(id)

    override suspend fun getEvent(date: Long): List<BaseEventModel> =
        eventDao.getEvents(date)

    override suspend fun getEvent(id: Int): BaseEventModel? = eventDao.getEvent(id)

    override suspend fun deleteEvent(id: Int): Boolean =
        runCatching { eventDao.deleteEvent(id) }.isSuccess

    override suspend fun deleteEvents(id: String): Boolean =
        runCatching { eventDao.deleteEvents(id) }.isSuccess

    override suspend fun deleteEvents(): Boolean =
        runCatching { eventDao.deleteEvents() }.isSuccess

    override suspend fun getEventsByDates(dates: LongRange): List<BaseEventModel> {
        val firstDate = dates.first.date
        val lastDate = dates.last.date
        return eventDao.getEvents(firstDate, lastDate)
    }

    override suspend fun createEvent(
        eventModel: BaseEventModel,
        addNotifications: Boolean
    ): Boolean {
        val result = runCatching {
            val id = UUID.randomUUID().toString()
            val color = RandomColors().color

            val events = if (addNotifications) {
                generateEvents(
                    eventModel = EventApiModel(eventModel, id, color)
                )
            } else {
                listOf(EventApiModel(eventModel, id, color))
            }

            eventDao.newEvent(events)
        }
        return result.isSuccess
    }

    override suspend fun getAllEvents(): List<BaseEventModel> =
        eventDao.getAllEvents().distinctBy {
            it.id
        }

    private fun generateEvents(eventModel: EventApiModel): List<EventApiModel> {
        val eventDate = Date(eventModel.date.date)
        val eventName = eventModel.name

        val list = listOf(
            eventModel.copy().apply {
                date = eventDate.time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_0).format(eventName)
                date = (eventDate + 1.week).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_1).format(eventName)
                date = (eventDate + 10.weeks).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_2).format(eventName)
                date = (eventDate + 100.weeks).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_3).format(eventName)
                date = (eventDate + 500.weeks).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_4).format(eventName)
                date = (eventDate + 2.months).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_5).format(eventName)
                date = (eventDate + 4.months).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_6).format(eventName)
                date = (eventDate + 7.months).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_7).format(eventName)
                date = (eventDate + 51.months).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_8).format(eventName)
                date = (eventDate + 101.months).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_9).format(eventName)
                date = (eventDate + 100.days).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_10).format(eventName)
                date = (eventDate + 500.days).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_11).format(eventName)
                date = (eventDate + 1000.days).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_12).format(eventName)
                date = (eventDate + 1.year).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_13).format(eventName)
                date = (eventDate + 2.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_14).format(eventName)
                date = (eventDate + 3.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_15).format(eventName)
                date = (eventDate + 4.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_16).format(eventName)
                date = (eventDate + 5.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_17).format(eventName)
                date = (eventDate + 6.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_18).format(eventName)
                date = (eventDate + 7.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_19).format(eventName)
                date = (eventDate + 8.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_20).format(eventName)
                date = (eventDate + 9.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_21).format(eventName)
                date = (eventDate + 10.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_22).format(eventName)
                date = (eventDate + 15.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_23).format(eventName)
                date = (eventDate + 20.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_24).format(eventName)
                date = (eventDate + 25.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_25).format(eventName)
                date = (eventDate + 30.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_26).format(eventName)
                date = (eventDate + 35.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_27).format(eventName)
                date = (eventDate + 40.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_28).format(eventName)
                date = (eventDate + 45.years).time
            },
            eventModel.copy().apply {
                name = context.getString(R.string.uevent_29).format(eventName)
                date = (eventDate + 50.years).time
            },
        ).sortedByDescending { it.date }

        return list
    }

    private val Long.date: Long
        get() {
            val cal = Calendar.getInstance().apply {
                timeInMillis = this@date
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }
            return cal.time.time
        }
}
