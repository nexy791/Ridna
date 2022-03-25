package com.ribsky.ridna.ui.relation

import android.graphics.drawable.ColorDrawable
import androidx.core.view.isGone
import androidx.fragment.app.setFragmentResultListener
import com.ribsky.domain.model.relation.BaseRelationModel
import com.ribsky.ridna.R
import com.ribsky.ridna.databinding.FragmentRelationBinding
import com.ribsky.ridna.ui.base.BaseFragment
import com.ribsky.ridna.ui.dialog.relation.EditRelationDialog
import com.ribsky.ridna.ui.settings.SettingsFragment
import com.ribsky.ridna.utils.ext.DateExtension.Companion.daysBetween
import com.ribsky.ridna.utils.ext.ImageExtension.Companion.load
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.color
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.colorAttr
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import org.koin.androidx.viewmodel.ext.android.viewModel

class RelationFragment :
    BaseFragment<RelationViewModel, FragmentRelationBinding>(FragmentRelationBinding::inflate) {

    override val viewModel: RelationViewModel by viewModel()

    override fun initViews() = with(binding) {

        setFragmentResultListener(EditRelationDialog.TAG) { requestKey, bundle ->

            if (requestKey == EditRelationDialog.TAG) {
                val result = bundle.getBoolean(EditRelationDialog.KEY)
                if (result) {
                    viewModel.getRelation()
                    eventNavigator.showSnackBar(string(R.string.relation_was_updated))
                }
            }
        }

        setFragmentResultListener(SettingsFragment.TAG) { requestKey, bundle ->

            if (requestKey == SettingsFragment.TAG) {

                val result = bundle.getBoolean(SettingsFragment.KEY)
                if (result) {
                    viewModel.getRelation()
                }
            }
        }

        layoutEmptyAll.setCallback {
            navigator.editRelationDialog()
        }
    }

    override fun initObserves() = with(viewModel) {
        getRelation()

        statusRelation.observe(viewLifecycleOwner) { model ->

            val isRelationExists = model != null

            binding.apply {
                container.isGone = !isRelationExists
                layoutEmptyAll.isGone = isRelationExists
            }

            if (isRelationExists) {
                fillInput(model!!)
            }
        }
    }

    private fun fillInput(model: BaseRelationModel) = with(binding) {
        val days = model.date.daysBetween

        tvDays.text = string(R.string.n_days).format(days)
        tvLabel.text = model.label

        tvFirstUser.text = model.firstUser.name
        tvSecondUser.text = model.secondUser.name

        ivFirstUser.apply {
            isGone = model.firstUser.image.isBlank()
            load(model.firstUser.image)
        }

        ivSecondUser.apply {
            isGone = model.secondUser.image.isBlank()
            load(model.secondUser.image)
        }

        if (model.banner.isNotBlank()) {
            ivBanner.load(model.banner)
            if (!model.isDark) {
                tvDays.setTextColor(color(R.color.black))
                tvLabel.setTextColor(color(R.color.black))
            } else {
                tvDays.setTextColor(color(R.color.white))
                tvLabel.setTextColor(color(R.color.white))
            }
        } else {
            ivBanner.setImageDrawable(ColorDrawable(colorAttr(com.google.android.material.R.attr.colorTertiaryContainer)))
        }

        goalView.setDays(days)
    }

    override fun clear() {
    }
}
