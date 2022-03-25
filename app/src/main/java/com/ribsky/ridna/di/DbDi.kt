package com.ribsky.ridna.di

import android.content.Context
import androidx.room.Room
import com.ribsky.data.db.event.EventDao
import com.ribsky.data.db.event.EventDataBase
import org.koin.dsl.module

val dbDi = module {

    single { provideEventDatabase(context = get()) }
    single { provideEventDao(database = get()) }
}

fun provideEventDatabase(context: Context): EventDataBase {
    return Room.databaseBuilder(context, EventDataBase::class.java, "events.db")
        .fallbackToDestructiveMigration()
        .build()
}

fun provideEventDao(database: EventDataBase): EventDao = database.eventDao
