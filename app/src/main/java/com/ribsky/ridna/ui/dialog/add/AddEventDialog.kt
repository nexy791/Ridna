package com.ribsky.ridna.ui.dialog.add

import android.app.Activity.RESULT_OK
import android.content.Intent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.cesarferreira.tempo.Tempo
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.ribsky.ridna.R
import com.ribsky.ridna.databinding.FragmentAddEventBinding
import com.ribsky.ridna.model.event.EventModel
import com.ribsky.ridna.ui.base.FullScreenBaseSheet
import com.ribsky.ridna.ui.pick.PickEventActivity
import com.ribsky.ridna.utils.ext.DateExtension.Companion.date
import com.ribsky.ridna.utils.ext.DateExtension.Companion.format
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.action
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.snackbar
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddEventDialog :
    FullScreenBaseSheet<FragmentAddEventBinding>(FragmentAddEventBinding::inflate) {

    private val viewModel: AddEventViewModel by viewModel()

    companion object {
        const val TAG = "ADD_EVENT_DIALOG"
        const val KEY = "KEY_EVENT"
        private const val TAG_TIME = "TIME"
    }

    private val event = EventModel.empty

    private val getEvent =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->

            if (result.resultCode == RESULT_OK) {

                result.data?.getStringExtra(PickEventActivity.KEY_EVENT)?.let { event ->
                    binding.tilName.editText!!.setText(event)
                }
            }
        }

    private val constraintsBuilder =
        CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now())

    private val datePickerDialog: MaterialDatePicker<Long> by lazy {
        MaterialDatePicker.Builder.datePicker()
            .setTitleText(string(R.string.dialog_pick_date))
            .setCalendarConstraints(constraintsBuilder.build())
            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
            .build()
    }

    override fun initViews() = with(binding) {

        event.date = Tempo.now.time.date

        chipAdd.text = event.date.format
        materialToolbar.setNavigationOnClickListener { dismiss() }

        datePickerDialog.apply {
            addOnPositiveButtonClickListener { ms ->
                val newDate = ms.date
                chipAdd.text = newDate.format
                event.date = newDate
            }
        }

        btnList.setOnClickListener {
            getEvent.launch(Intent(requireContext(), PickEventActivity::class.java))
        }

        chipAdd.setOnClickListener {
            datePickerDialog.show(childFragmentManager, TAG_TIME)
        }

        fabDone.setOnClickListener {

            event.apply {
                name = tilName.editText!!.text.toString()
                description = tilDescription.editText!!.text.toString()
                addNotifications = checkBox.isChecked
            }

            val isNameBlank = event.name.isBlank()

            if (!isNameBlank) {
                viewModel.createNewEvent(event)
            } else {
                showMessage(string(R.string.event_cant_be_empty))
            }
        }
    }

    override fun initObserves() = with(viewModel) {

        statusNewEvent.observe(viewLifecycleOwner) { status ->
            when (status) {
                true -> {
                    setFragmentResult(TAG, bundleOf(KEY to status))
                    dismiss()
                }
                false -> showMessage(string(R.string.unknown_error))
            }
        }
    }

    private fun showMessage(text: String) = binding.root.snackbar(
        text, Snackbar.LENGTH_SHORT
    ) {
        anchorView = binding.fabDone
        action("Ок") {}
    }

    override fun clear() {
    }
}
