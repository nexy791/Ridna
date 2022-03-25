package com.ribsky.ridna.di

import com.ribsky.data.repository.EventRepositoryImpl
import com.ribsky.data.repository.FileRepositoryImpl
import com.ribsky.data.repository.RelationRepositoryImpl
import com.ribsky.data.service.file.FileService
import com.ribsky.data.service.file.FileServiceImpl
import com.ribsky.domain.repository.EventRepository
import com.ribsky.domain.repository.FileRepository
import com.ribsky.domain.repository.RelationRepository
import org.koin.dsl.module

val dataDi = module {

    single<EventRepository> {
        EventRepositoryImpl(
            eventDao = get(),
            context = get()
        )
    }

    single<FileRepository> {
        FileRepositoryImpl(
            context = get(),
            fileService = get()
        )
    }

    single<RelationRepository> {
        RelationRepositoryImpl(
            sharedPreferences = get(),
            moshi = get()
        )
    }

    single<FileService> {
        FileServiceImpl(
            context = get()
        )
    }
}
