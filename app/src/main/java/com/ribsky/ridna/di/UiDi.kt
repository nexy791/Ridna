package com.ribsky.ridna.di

import com.ribsky.ridna.navigator.Navigator
import com.ribsky.ridna.navigator.NavigatorImpl
import com.ribsky.ridna.ui.dialog.add.AddEventViewModel
import com.ribsky.ridna.ui.dialog.event.EventViewModel
import com.ribsky.ridna.ui.dialog.relation.EditRelationViewModel
import com.ribsky.ridna.ui.events.EventsViewModel
import com.ribsky.ridna.ui.main.MainViewModel
import com.ribsky.ridna.ui.relation.RelationViewModel
import com.ribsky.ridna.ui.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val uiDi = module {

    viewModel {
        MainViewModel(
            getUpcomingEventUseCase = get(),
            getEventsByDays = get(),
            getEventsByDay = get(),
        )
    }

    viewModel {
        EventViewModel(
            getEventByIdUseCase = get(),
            getEventsByIdUseCase = get(),
            deleteEventUseCase = get(),
        )
    }

    viewModel {
        EventsViewModel(
            getAllEventsUseCase = get()
        )
    }

    viewModel {
        EditRelationViewModel(
            relationUseCase = get(),
            createFileUseCase = get()
        )
    }

    viewModel {
        RelationViewModel(
            getRelationUseCase = get()
        )
    }

    viewModel {
        AddEventViewModel(
            createEventUseCase = get()
        )
    }

    viewModel {
        SettingsViewModel(
            deleteAllUseCase = get()
        )
    }

    factory<Navigator> {
        NavigatorImpl()
    }
}
