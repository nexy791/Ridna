package com.ribsky.ridna.di

import com.ribsky.domain.usecase.delete.DeleteAllUseCase
import com.ribsky.domain.usecase.delete.DeleteAllUseCaseImpl
import com.ribsky.domain.usecase.event.*
import com.ribsky.domain.usecase.event.delete.DeleteEventByIdUseCase
import com.ribsky.domain.usecase.event.delete.DeleteEventByIdUseCaseImpl
import com.ribsky.domain.usecase.event.delete.DeleteEventsByIdUseCase
import com.ribsky.domain.usecase.event.delete.DeleteEventsByIdUseCaseImpl
import com.ribsky.domain.usecase.file.CreateFileUseCase
import com.ribsky.domain.usecase.file.CreateFileUseCaseImpl
import com.ribsky.domain.usecase.relation.*
import org.koin.dsl.module

val domainDi = module {

    factory<GetUpcomingEventUseCase> {
        GetUpcomingEventUseCaseImpl(
            eventRepository = get()
        )
    }

    factory<CreateEventUseCase> {
        CreateEventUseCaseImpl(
            eventRepository = get()
        )
    }

    factory<GetEventsByDays> {
        GetEventsByDaysImpl(
            eventRepository = get()
        )
    }

    factory<GetEventsByDay> {
        GetEventsByDayImpl(
            eventRepository = get()
        )
    }

    factory<GetEventByIdUseCase> {
        GetEventByIdUseCaseImpl(
            eventRepository = get()
        )
    }

    factory<GetEventsByIdUseCase> {
        GetEventsByIdUseCaseImpl(
            eventRepository = get()
        )
    }

    factory<GetAllEventsUseCase> {
        GetAllEventsUseCaseImpl(
            eventRepository = get()
        )
    }

    factory<CreateFileUseCase> {
        CreateFileUseCaseImpl(
            fileRepository = get()
        )
    }

    factory<EditRelationUseCase> {
        EditRelationUseCaseImpl(
            relationRepository = get()
        )
    }

    factory<GetRelationUseCase> {
        GetRelationUseCaseImpl(
            relationRepository = get()
        )
    }

    factory<DeleteEventsByIdUseCase> {
        DeleteEventsByIdUseCaseImpl(
            eventRepository = get()
        )
    }

    factory<DeleteEventByIdUseCase> {
        DeleteEventByIdUseCaseImpl(
            eventRepository = get()
        )
    }

    factory<RelationUseCase> {
        RelationUseCaseImpl(
            editRelationUseCase = get(),
            getRelationUseCase = get()
        )
    }

    factory<DeleteAllUseCase> {
        DeleteAllUseCaseImpl(
            eventRepository = get(),
            relationRepository = get()
        )
    }

    factory<DeleteEventUseCase> {
        DeleteEventUseCaseImpl(
            deleteEventByIdUseCase = get(),
            deleteEventsByIdUseCase = get()
        )
    }
}
