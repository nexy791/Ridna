package com.ribsky.ridna.model.settings

import android.content.Context
import androidx.recyclerview.widget.DiffUtil
import com.ribsky.ridna.R
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string

sealed class Settings {

    enum class Type {
        HELP, LANG, RATE, SHARE, UPDATE, DELETE, EMAIL, POLICY, LIBRARIES, VERSION
    }

    data class SettingsModel(
        val icon: Int,
        val title: String,
        val label: String,
        val type: Type
    ) : Settings() {

        companion object {

            val Context.defaultSettingsList
                get() = listOf(
                    LabelModel(string(R.string.settings_label_app)),
                    SettingsModel(
                        R.drawable.ic_outline_supervised_user_circle_24,
                        string(R.string.settings_title_help),
                        string(R.string.settings_label_help),
                        Type.HELP
                    ),
                    SettingsModel(
                        R.drawable.ic_round_language_24,
                        string(R.string.settings_title_lang),
                        string(R.string.settings_label_lang),
                        Type.LANG
                    ),
                    SettingsModel(
                        R.drawable.ic_round_favorite_border_24,
                        string(R.string.settings_title_rate),
                        string(R.string.settings_label_rate),
                        Type.RATE
                    ),
                    SettingsModel(
                        R.drawable.ic_outline_share_24,
                        string(R.string.settings_title_share),
                        string(R.string.settings_label_share),
                        Type.SHARE
                    ),
                    SettingsModel(
                        R.drawable.ic_round_update_24,
                        string(R.string.settings_title_update),
                        string(R.string.settings_label_update),
                        Type.UPDATE
                    ),
                    LabelModel(string(R.string.settings_label_data)),
                    SettingsModel(
                        R.drawable.ic_outline_delete_sweep_24,
                        string(R.string.settings_title_delete),
                        string(R.string.settings_label_delete),
                        Type.DELETE
                    ),
                    LabelModel(getString(R.string.settings_label_email)),
                    SettingsModel(
                        R.drawable.ic_outline_email_24,
                        string(R.string.settings_title_email),
                        string(R.string.settings_label_email2),
                        Type.EMAIL
                    ),
                    LabelModel(getString(R.string.settings_label_etc)),
                    SettingsModel(
                        R.drawable.ic_outline_privacy_tip_24,
                        string(R.string.settings_title_privacy),
                        string(R.string.settings_label_privacy),
                        Type.POLICY
                    ),
                    SettingsModel(
                        R.drawable.ic_outline_local_library_24,
                        string(R.string.settings_title_lib),
                        string(R.string.settings_label_lib),
                        Type.LIBRARIES
                    ),
                    SettingsModel(
                        R.drawable.ic_outline_info_24,
                        string(R.string.settings_title_version),
                        string(R.string.settings_label_version),
                        Type.VERSION
                    ),
                )

            object DiffCallback : DiffUtil.ItemCallback<Settings>() {
                override fun areItemsTheSame(
                    oldItem: Settings,
                    newItem: Settings
                ): Boolean =
                    oldItem == newItem

                override fun areContentsTheSame(
                    oldItem: Settings,
                    newItem: Settings
                ): Boolean =
                    oldItem == newItem
            }
        }
    }

    data class LabelModel(val title: String) : Settings()
}
