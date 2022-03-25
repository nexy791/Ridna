package com.ribsky.ridna.ui.dialog.event

import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ConcatAdapter
import com.google.android.material.snackbar.Snackbar
import com.ribsky.data.model.event.EventApiModel
import com.ribsky.ridna.R
import com.ribsky.ridna.adapter.FadeInLinearLayoutManager
import com.ribsky.ridna.adapter.event.EventHeaderAdapter
import com.ribsky.ridna.adapter.event.EventListAdapter
import com.ribsky.ridna.databinding.FragmentEventBinding
import com.ribsky.ridna.ui.base.BaseSheet
import com.ribsky.ridna.utils.ShareView
import com.ribsky.ridna.utils.ShareView.Builder.footerText
import com.ribsky.ridna.utils.ext.DateExtension.Companion.format
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.action
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.alert
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.negativeButton
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.positiveButton
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.snackbar
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import org.koin.androidx.viewmodel.ext.android.viewModel

class EventDialog : BaseSheet<FragmentEventBinding>(FragmentEventBinding::inflate) {

    private val args: EventDialogArgs by navArgs()
    private val viewModel: EventViewModel by viewModel()

    private var adapterHeader: EventHeaderAdapter? = null
    private var adapterList: EventListAdapter? = null
    private var concatAdapter: ConcatAdapter? = null

    private var shareView: ShareView? = null

    private var requireId: Int? = null

    companion object {
        const val KEY = "KEY_RESULT"
        const val KEY_ID = "id"
        const val TAG = "EVENT_DIALOG"
    }

    override fun initViews(): Unit = with(binding) {
        initAdapters()
        initRecycler()
    }

    private fun initRecycler() = binding.recyclerView.apply {
        layoutManager = FadeInLinearLayoutManager(activity)
        adapter = concatAdapter
    }

    private fun initAdapters() {
        adapterHeader = EventHeaderAdapter()
        adapterList = EventListAdapter { id ->
            requireId = id
            dismiss()
        }
        concatAdapter = ConcatAdapter(adapterHeader, adapterList)
    }

    private fun initToolbar(event: EventApiModel) = with(binding.materialToolbar) {
        setNavigationOnClickListener { dismiss() }
        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_share -> share(event)
                R.id.menu_delete -> alertDelete(event)
                R.id.menu_delete_all -> alertDeleteAll(event)
            }
            return@setOnMenuItemClickListener true
        }
    }

    private fun share(event: EventApiModel) {
        val date = event.date.format
        val text =
            "$date - ${event.name}\n\n${activity.footerText}"

        shareView = ShareView
            .addName(text)
            .addContext(activity)
            .build().apply {
                share(activity)
            }
    }

    private fun alertDelete(event: EventApiModel) {
        alert {
            setTitle(string(R.string.event_dialog_delete_title))
            setIcon(R.drawable.ic_outline_delete_sweep_24)
            setMessage(string(R.string.event_dialog_delete_one).format(event.date.format))
            positiveButton(string(R.string.event_dialog_cancel))
            negativeButton(string(R.string.event_dialog_delete)) {
                viewModel.deleteEvent(event.uid)
            }
        }
    }

    private fun alertDeleteAll(event: EventApiModel) {
        alert {
            setTitle(string(R.string.event_dialog_delete_title))
            setIcon(R.drawable.ic_outline_delete_sweep_24)
            setMessage(string(R.string.event_dialog_delete_all))
            positiveButton(string(R.string.event_dialog_cancel))
            negativeButton(string(R.string.event_dialog_delete)) {
                viewModel.deleteAllEvent(event.id)
            }
        }
    }

    override fun initObserves() = with(viewModel) {
        getEvent(args.id)
        statusEventInfo.observe(viewLifecycleOwner) { event ->
            if (event != null) {
                adapterHeader?.submitList(listOf(event))
                binding.materialToolbar.title = event.date.format
                initToolbar(event as EventApiModel)
            } else {
                dismiss()
            }
        }
        statusEvents.observe(viewLifecycleOwner) { events ->
            adapterList?.submitList(events)
        }
        statusDeleteEvent.observe(viewLifecycleOwner) { status ->
            when (status) {
                true -> {
                    setFragmentResult(TAG, bundleOf(KEY to status))
                    dismiss()
                }
                false -> showMessage(string(R.string.throw_error))
            }
        }
    }

    override fun onStop() {
        super.onStop()
        requireId?.let { navigator.openEventDetails(it) }
    }

    private fun showMessage(text: String) = binding.root.snackbar(
        text, Snackbar.LENGTH_SHORT
    ) {
        action(string(R.string.snack_ok)) {}
    }

    override fun clear() {
        adapterHeader = null
        adapterList = null
        concatAdapter = null
        shareView?.clear()
    }
}
