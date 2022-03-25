package com.ribsky.ridna.ui.events

import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.ribsky.ridna.R
import com.ribsky.ridna.adapter.event.EventAllAdapter
import com.ribsky.ridna.databinding.FragmentEventsBinding
import com.ribsky.ridna.ui.base.BaseFragment
import com.ribsky.ridna.ui.dialog.add.AddEventDialog
import com.ribsky.ridna.ui.dialog.event.EventDialog
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventsFragment :
    BaseFragment<EventsViewModel, FragmentEventsBinding>(FragmentEventsBinding::inflate) {

    override val viewModel: EventsViewModel by viewModel()

    companion object {
        const val TAG = "EVENTS_FRAGMENT"
        const val KEY = "KEY_EVENTS"
    }

    private var adapterList: EventAllAdapter? = null

    override fun initViews() {

        setFragmentResultListener(AddEventDialog.TAG) { requestKey, bundle ->

            if (requestKey == AddEventDialog.TAG) {

                val result = bundle.getBoolean(AddEventDialog.KEY)
                if (result) {
                    viewModel.loadEvents()
                    eventNavigator.showSnackBar(string(R.string.add_success))
                }
            }
        }

        setFragmentResultListener(EventDialog.TAG) { requestKey, bundle ->

            if (requestKey == EventDialog.TAG) {

                val result = bundle.getBoolean(EventDialog.KEY)
                if (result) {
                    viewModel.loadEvents()
                    eventNavigator.showSnackBar(string(R.string.list_was_updated))
                    setFragmentResult(TAG, bundleOf(KEY to result))
                }
            }
        }

        initAdapters()
        initRecycler()
    }

    private fun initRecycler() = binding.recyclerView.apply {
        layoutManager = LinearLayoutManager(activity)
        adapter = adapterList
    }

    private fun initAdapters() {
        adapterList = EventAllAdapter { id ->
            navigator.openEventDetails(id)
        }
    }

    override fun initObserves() = with(viewModel) {
        loadEvents()
        eventsStatus.observe(viewLifecycleOwner) { events ->
            adapterList?.submitList(events)
            binding.recyclerView.isGone = events.isEmpty()
            if (events.isEmpty()) {
                activity.onBackPressed()
            }
        }
    }

    override fun clear() {
    }
}
