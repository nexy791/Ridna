package com.ribsky.data.db.event

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ribsky.data.model.event.EventApiModel

@Dao
interface EventDao {

    @Query("SELECT * FROM eventapimodel WHERE date >= :date ORDER BY date ASC LIMIT 1")
    suspend fun getEvent(date: Long): EventApiModel?

    @Insert
    suspend fun newEvent(eventApiModel: EventApiModel)

    @Insert
    suspend fun newEvent(eventApiModel: List<EventApiModel>)

    @Query("SELECT * FROM eventapimodel WHERE date >= :firstDate AND date <= :lastDate ORDER BY date ASC")
    suspend fun getEvents(firstDate: Long, lastDate: Long): List<EventApiModel>

    @Query("SELECT * FROM eventapimodel WHERE date = :date ORDER BY date ASC")
    suspend fun getEvents(date: Long): List<EventApiModel>

    @Query("SELECT * FROM eventapimodel WHERE uid = :id")
    suspend fun getEvent(id: Int): EventApiModel?

    @Query("SELECT * FROM eventapimodel WHERE id = :id ORDER BY date ASC")
    suspend fun getEvents(id: String): List<EventApiModel>

    @Query("SELECT * FROM eventapimodel ORDER BY date ASC")
    suspend fun getAllEvents(): List<EventApiModel>

    @Query("DELETE FROM eventapimodel WHERE uid = :id")
    suspend fun deleteEvent(id: Int)

    @Query("DELETE FROM eventapimodel WHERE id = :id")
    suspend fun deleteEvents(id: String)

    @Query("DELETE FROM eventapimodel")
    suspend fun deleteEvents()
}
