package com.ribsky.ridna.ui.settings

import android.content.Intent
import android.content.Intent.*
import android.net.Uri
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.ribsky.ridna.R
import com.ribsky.ridna.adapter.FadeInLinearLayoutManager
import com.ribsky.ridna.adapter.settings.SettingsAdapter
import com.ribsky.ridna.databinding.FragmentSettingsBinding
import com.ribsky.ridna.model.settings.Settings
import com.ribsky.ridna.model.settings.Settings.SettingsModel.Companion.defaultSettingsList
import com.ribsky.ridna.ui.base.BaseFragment
import com.ribsky.ridna.utils.ShareView
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.alert
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.negativeButton
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.positiveButton
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import org.koin.androidx.viewmodel.ext.android.viewModel

class SettingsFragment :
    BaseFragment<SettingsViewModel, FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    override val viewModel: SettingsViewModel by viewModel()

    private val settings by lazy { activity.defaultSettingsList }
    private var adapter: SettingsAdapter? = null

    private var shareView: ShareView? = null

    companion object {
        const val KEY = "KEY_RESULT"
        const val TAG = "EVENT_SETTINGS"
    }

    override fun initViews() {
        initRecyclerView()
    }

    private fun initRecyclerView() = with(binding.recyclerView) {

        this@SettingsFragment.adapter = SettingsAdapter { type ->
            when (type) {
                Settings.Type.HELP -> navigator.openHelp(activity)
                Settings.Type.LANG -> showTranslateAppDialog()
                Settings.Type.RATE -> navigator.openRate(activity)
                Settings.Type.SHARE -> shareApp()
                Settings.Type.UPDATE -> navigator.updateApp(activity)
                Settings.Type.DELETE -> deleteAll()
                Settings.Type.EMAIL -> sendEmail()
                Settings.Type.POLICY -> navigator.openPrivacy(activity)
                Settings.Type.LIBRARIES -> navigator.openLibraries()
                Settings.Type.VERSION -> navigator.openAppSettings(activity)
            }
        }.apply {
            submitList(settings)
        }

        setHasFixedSize(true)
        layoutManager = FadeInLinearLayoutManager(activity)
        adapter = this@SettingsFragment.adapter
    }

    private fun showTranslateAppDialog() {
        val langs = arrayOf(
            string(R.string.settings_lang_en_code), string(R.string.settings_lang_uk_code)
        )

        alert {
            setIcon(R.drawable.ic_round_language_24)
            setTitle(string(R.string.settings_lang_title))
            setItems(langs) { _, position ->
                when (position) {
                    0 -> activity.setLanguage("en")
                    1 -> activity.setLanguage("uk")
                }
            }
            positiveButton(string(R.string.dialog_pick_cancel))
        }
    }

    private fun shareApp() {
        shareView = ShareView
            .addContext(activity)
            .addName(string(R.string.default_share_text).format(activity.packageName))
            .build().apply {
                share(activity)
            }
    }

    private fun sendEmail() {
        Intent(ACTION_SENDTO).apply {
            data = Uri.parse("mailto:nexy791@gmail.com")
            flags = FLAG_ACTIVITY_NEW_TASK
            putExtra(EXTRA_SUBJECT, "Ridna App")
            startActivity(createChooser(this, null))
        }
    }

    private fun deleteAll() {
        alert {
            setTitle(string(R.string.dialog_delete_data_title))
            setMessage(string(R.string.dialog_delete_data_body))
            setIcon(R.drawable.ic_outline_delete_sweep_24)
            positiveButton(string(R.string.dialog_delete_data_cancel))
            negativeButton(string(R.string.dialog_delete_data_action)) {
                viewModel.deleteAll()
            }
        }
    }

    override fun initObserves() = with(viewModel) {
        statusDelete.observe(viewLifecycleOwner) { status ->
            when (status) {
                true -> {
                    eventNavigator.showSnackBar(string(R.string.data_deleted))
                    setFragmentResult(TAG, bundleOf(KEY to status))
                }
                false -> eventNavigator.showSnackBar(string(R.string.throw_error))
            }
        }
    }

    override fun clear() {
        shareView = null
        adapter = null
    }
}
