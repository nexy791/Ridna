package com.ribsky.ridna.ui.dialog.relation

import android.net.Uri
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.fragment.app.setFragmentResult
import androidx.palette.graphics.Palette
import com.cesarferreira.tempo.Tempo
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.snackbar.Snackbar
import com.ribsky.data.model.relation.RelationApiModel
import com.ribsky.ridna.R
import com.ribsky.ridna.databinding.FragmentEditRelationBinding
import com.ribsky.ridna.model.relation.RelationModel
import com.ribsky.ridna.ui.base.FullScreenBaseSheet
import com.ribsky.ridna.utils.ext.DateExtension.Companion.date
import com.ribsky.ridna.utils.ext.DateExtension.Companion.format
import com.ribsky.ridna.utils.ext.ImageExtension.Companion.getBitmapFromUri
import com.ribsky.ridna.utils.ext.ImageExtension.Companion.isDark
import com.ribsky.ridna.utils.ext.ImageExtension.Companion.load
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.action
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.color
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.snackbar
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import org.koin.androidx.viewmodel.ext.android.viewModel

class EditRelationDialog :
    FullScreenBaseSheet<FragmentEditRelationBinding>(FragmentEditRelationBinding::inflate) {

    companion object {
        const val TAG = "EDIT_RELATION_DIALOG"
        const val KEY = "KEY_RELATION"
        private const val TAG_TIME = "TIME"
        private const val TAG_CONTRACT = "TAG_CONTRACT"
    }

    sealed class ImageType {
        object FirstImageType : ImageType()
        object SecondImageType : ImageType()
        object BackgroundImageType : ImageType()
    }

    private val viewModel: EditRelationViewModel by viewModel()

    private var relationModel: RelationModel = RelationModel.empty
    private var editRelationHelper: EditRelationHelper? = null

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

        relationModel.date = Tempo.now.time.date
        chipAdd.text = relationModel.date.format

        ivBackgroundSwitcher.setOnClickListener {
            switchDark(!relationModel.isDark)
        }

        ivFirst.setOnClickListener {
            editRelationHelper?.open(ImageType.FirstImageType)
        }

        ivSecond.setOnClickListener {
            editRelationHelper?.open(ImageType.SecondImageType)
        }

        ivBackground.setOnClickListener {
            editRelationHelper?.open(ImageType.BackgroundImageType)
        }

        chipReset.setOnClickListener {
            ivFirst.setImageDrawable(null)
            ivFirstIcon.isGone = false
            relationModel.apply {
                tempUriOfBanner = null
                banner = ""
                firstUser.apply {
                    tempUriOfImage = null
                    image = ""
                }
                secondUser.apply {
                    tempUriOfImage = null
                    image = ""
                }
            }

            ivSecond.setImageDrawable(null)
            ivSecondIcon.isGone = false

            backgroundContainer.setImageDrawable(null)
            switchDark(false)
            showMessage(string(R.string.image_reset))
        }

        editRelationHelper = EditRelationHelper(
            activity.activityResultRegistry,
            TAG_CONTRACT,
            object : EditRelationHelper.Callback {
                override fun onSuccess(id: ImageType, uri: Uri) {
                    when (id) {
                        ImageType.FirstImageType -> {
                            relationModel.firstUser.apply {
                                tempUriOfImage = uri
                                image = ""
                            }
                            ivFirst.load(uri)
                            ivFirstIcon.isGone = true
                        }
                        ImageType.SecondImageType -> {
                            relationModel.secondUser.apply {
                                tempUriOfImage = uri
                                image = ""
                            }
                            ivSecond.load(uri)
                            ivSecondIcon.isGone = true
                        }
                        ImageType.BackgroundImageType -> {
                            relationModel.apply {
                                tempUriOfBanner = uri
                                banner = ""
                            }
                            backgroundContainer.load(uri)
                            loadPalette(uri)
                        }
                    }
                }

                override fun onError() {
                    showMessage(string(R.string.error_image))
                }
            }
        )
        lifecycle.addObserver(editRelationHelper!!)

        materialToolbar.setNavigationOnClickListener { dismiss() }

        datePickerDialog.apply {
            addOnPositiveButtonClickListener { ms ->
                chipAdd.text = ms.date.format
                relationModel.date = ms.date
            }
        }

        chipAdd.setOnClickListener {
            datePickerDialog.show(childFragmentManager, TAG_TIME)
        }

        fabDone.setOnClickListener {
            getInput()

            val firstName = tilName.editText!!.text.toString().isNotBlank()
            val secondName = tilNameSecond.editText!!.text.toString().isNotBlank()

            if (firstName && secondName) {
                viewModel.editRelation(relationModel)
            } else {
                showMessage(string(R.string.error_name))
            }
        }
    }

    private fun loadPalette(uri: Uri) {
        activity.getBitmapFromUri(uri) { bitmap ->
            val builder = bitmap?.let { Palette.Builder(it) }
            builder?.generate { palette: Palette? ->
                val isDark = palette?.dominantSwatch?.rgb?.isDark
                isDark?.let {
                    switchDark(it)
                }
            }
        }
    }

    override fun initObserves() = with(viewModel) {
        getRelation()

        statusEditRelation.observe(viewLifecycleOwner) { status ->
            when {
                status.isSuccess -> {
                    setFragmentResult(TAG, bundleOf(KEY to true))
                    dismiss()
                }
                status.isFailure -> showMessage(
                    getString(R.string.error_description).format(
                        status.exceptionOrNull()?.localizedMessage ?: string(R.string.unknown_error)
                    )
                )
            }
        }

        statusRelation.observe(viewLifecycleOwner) {
            (it as? RelationApiModel)?.let { relation ->
                fillInput(relation)
            }
        }
    }

    private fun getInput() = with(binding) {
        val firstName = tilName.editText!!.text.toString()
        val secondName = tilNameSecond.editText!!.text.toString()
        val label = tilDescription.editText!!.text.toString()

        relationModel.apply {
            firstUser = firstUser.apply {
                name = firstName
            }
            secondUser = secondUser.apply {
                name = secondName
            }
            this.label = label
        }
    }

    private fun fillInput(model: RelationApiModel) = with(binding) {

        tilName.editText!!.setText(model.firstUser.name)
        tilNameSecond.editText!!.setText(model.secondUser.name)
        tilDescription.editText!!.setText(model.label)

        relationModel.date = model.date
        chipAdd.text = model.date.format

        switchDark(model.isDark)

        if (model.banner.isNotBlank()) {
            relationModel.apply {
                tempUriOfBanner = null
                banner = model.banner
            }
            backgroundContainer.load(model.banner)
        }

        if (model.firstUser.image.isNotBlank()) {
            relationModel.firstUser.apply {
                tempUriOfImage = null
                image = model.firstUser.image
            }
            ivFirst.load(model.firstUser.image)
            ivFirstIcon.isGone = true
        }

        if (model.secondUser.image.isNotBlank()) {
            relationModel.secondUser.apply {
                tempUriOfImage = null
                image = model.secondUser.image
            }
            ivSecond.load(model.secondUser.image)
            ivSecondIcon.isGone = true
        }
    }

    private fun switchDark(isDark: Boolean) = with(binding) {
        relationModel.isDark = isDark
        if (isDark) {
            tvTitle.setTextColor(color(R.color.white))
            tvBody.setTextColor(color(R.color.white))
            ivBackgroundSwitcherIcon.setImageResource(R.drawable.ic_outline_dark_mode_24)
        } else {
            tvTitle.setTextColor(color(R.color.black))
            tvBody.setTextColor(color(R.color.black))
            ivBackgroundSwitcherIcon.setImageResource(R.drawable.ic_outline_wb_sunny_24)
        }
    }

    private fun showMessage(text: String) = binding.root.snackbar(
        text, Snackbar.LENGTH_SHORT
    ) {
        anchorView = binding.fabDone
        action(string(R.string.snack_ok)) {}
    }

    override fun clear() {
        lifecycle.removeObserver(editRelationHelper!!)
        editRelationHelper = null
    }
}
