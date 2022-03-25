package com.ribsky.data.db.event

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ribsky.data.model.event.EventApiModel

@Database(entities = [EventApiModel::class], version = 1, exportSchema = false)
abstract class EventDataBase : RoomDatabase() {

    abstract val eventDao: EventDao
}
