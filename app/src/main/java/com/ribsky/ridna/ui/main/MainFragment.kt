package com.ribsky.ridna.ui.main

import android.view.View
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import com.ribsky.data.model.event.EventApiModel
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.ridna.R
import com.ribsky.ridna.databinding.FragmentMainBinding
import com.ribsky.ridna.ui.base.BaseFragment
import com.ribsky.ridna.ui.dialog.add.AddEventDialog
import com.ribsky.ridna.ui.dialog.event.EventDialog
import com.ribsky.ridna.ui.events.EventsFragment
import com.ribsky.ridna.ui.settings.SettingsFragment
import com.ribsky.ridna.ui.view.DayView
import com.ribsky.ridna.ui.view.calendar.CalendarView
import com.ribsky.ridna.utils.ShareView
import com.ribsky.ridna.utils.ShareView.Builder.footerText
import com.ribsky.ridna.utils.ext.DateExtension.Companion.date
import com.ribsky.ridna.utils.ext.DateExtension.Companion.format
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.alert
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.positiveButton
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.Date

class MainFragment :
    BaseFragment<MainViewModel, FragmentMainBinding>(FragmentMainBinding::inflate) {

    override val viewModel: MainViewModel by viewModel()

    private var shareView: ShareView? = null

    override fun initViews() = with(binding) {

        initListeners()

        dayView.setCallback(object : DayView.Callback {
            override fun onAddEvent() {
                navigator.addEventDialog()
            }

            override fun onClick(event: BaseEventModel) {
                if (event is EventApiModel) {
                    navigator.openEventDetails(event.uid)
                }
            }

            override fun onShare(view: View, event: BaseEventModel) {
                val date = event.date.format
                val text =
                    "$date - ${event.name}\n\n${activity.footerText}"
                shareView = ShareView
                    .addView(view)
                    .addName(text)
                    .addContext(activity)
                    .build().apply {
                        share(activity)
                    }
            }
        })

        bannerViewAll.setCallback {
            navigator.openAllEvents()
        }

        bannerView.setCallback { date ->
            viewModel.getUpcomingEvents(date)
        }

        layoutEmptyAll.setCallback {
            navigator.addEventDialog()
        }
        calendarView.setCallback(object : CalendarView.Callback {
            override fun onFilled(dates: LongRange) {
                viewModel.getCalendarEvents(dates)
            }

            override fun onClicked(date: Long) {
                viewModel.getEventByDate(date)
            }
        })
        calendarView.fillCalendar()
        viewModel.getEventByDate(Date().time.date)
    }

    private fun initListeners() {
        setFragmentResultListener(AddEventDialog.TAG) { requestKey, bundle ->

            if (requestKey == AddEventDialog.TAG) {

                val result = bundle.getBoolean(AddEventDialog.KEY)
                if (result) {
                    viewModel.updateInfo()
                    eventNavigator.showSnackBar(string(R.string.add_success))
                }
            }
        }

        setFragmentResultListener(SettingsFragment.TAG) { requestKey, bundle ->

            if (requestKey == SettingsFragment.TAG) {

                val result = bundle.getBoolean(SettingsFragment.KEY)
                if (result) {
                    viewModel.getUpcomingEvent()
                }
            }
        }

        setFragmentResultListener(EventsFragment.TAG) { requestKey, bundle ->

            if (requestKey == EventsFragment.TAG) {

                val result = bundle.getBoolean(EventsFragment.KEY)
                if (result) {
                    viewModel.updateInfo()
                }
            }
        }

        setFragmentResultListener(EventDialog.TAG) { requestKey, bundle ->

            if (requestKey == EventDialog.TAG) {

                val result = bundle.getBoolean(EventDialog.KEY)
                if (result) {
                    viewModel.updateInfo()
                    eventNavigator.showSnackBar(string(R.string.list_was_updated))
                    setFragmentResult(EventsFragment.TAG, bundleOf(EventsFragment.KEY to result))
                }
            }
        }
    }

    override fun initObserves() = with(viewModel) {

        updateInfo()

        statusUpcomingEvent.observe(viewLifecycleOwner) { event ->
            val isEventExists = event != null

            binding.apply {
                container.isGone = !isEventExists
                layoutEmptyAll.isGone = isEventExists
            }
            event?.let {
                binding.bannerView.setEvent(it)
            }
        }
        statusCalendarEvents.observe(viewLifecycleOwner) { dates ->
            binding.calendarView.updateEvents(dates)
        }
        statusDayEvents.observe(viewLifecycleOwner) { events ->
            binding.dayView.submitAdapter(events)
        }
        statusUpcomingEvents.observe(viewLifecycleOwner) { events ->
            val isOneItem = events.size == 1
            if (isOneItem) {
                navigator.openEventDetails((events.first() as EventApiModel).uid)
            } else {
                showPickEvents(events)
            }
        }
    }

    private fun showPickEvents(events: List<BaseEventModel>) {

        val eventsNames = events.map {
            if (it.name.length > 50) it.name.take(47) + "..." else it.name
        }.toTypedArray()

        alert {
            setIcon(R.drawable.ic_outline_auto_awesome_24)
            setTitle(string(R.string.dialog_pick_event))
            setItems(eventsNames) { _, position ->
                navigator.openEventDetails((events[position] as EventApiModel).uid)
            }
            positiveButton(string(R.string.dialog_pick_cancel))
        }
    }

    override fun clear() {
        shareView?.clear()
    }
}
