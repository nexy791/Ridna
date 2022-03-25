package com.ribsky.ridna.adapter.settings

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.ridna.databinding.ItemSettingsBinding
import com.ribsky.ridna.databinding.ItemSettingsLabelBinding
import com.ribsky.ridna.model.settings.Settings

class SettingsAdapter(private val onClickListener: Callback) :
    ListAdapter<Settings, RecyclerView.ViewHolder>(Settings.SettingsModel.Companion.DiffCallback) {

    fun interface Callback {
        fun onClick(model: Settings.Type)
    }

    companion object {
        private const val TYPE_LABEl = 0
        private const val TYPE_SETTINGS = 1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            TYPE_SETTINGS -> SettingsHolder.create(parent)
            TYPE_LABEl -> SettingsLabel.create(parent)
            else -> error("Bad ViewHolder")
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when (holder) {
            is SettingsHolder -> holder.bind(item as Settings.SettingsModel, onClickListener)
            is SettingsLabel -> holder.bind(item as Settings.LabelModel)
        }
    }

    private class SettingsHolder(private val binding: ItemSettingsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Settings.SettingsModel, callback: Callback) {
            binding.apply {
                root.setOnClickListener {
                    callback.onClick(item.type)
                }
                tvTitle.text = item.title
                tvDescription.text = item.label
                ivSettings.setImageResource(item.icon)
            }
        }

        companion object {
            fun create(parent: ViewGroup): SettingsHolder {
                val binding = ItemSettingsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SettingsHolder(binding)
            }
        }
    }

    private class SettingsLabel(private val binding: ItemSettingsLabelBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Settings.LabelModel) {
            binding.apply {
                tvTitle.text = item.title
            }
        }

        companion object {
            fun create(parent: ViewGroup): SettingsLabel {
                val binding = ItemSettingsLabelBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return SettingsLabel(binding)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is Settings.SettingsModel -> TYPE_SETTINGS
            is Settings.LabelModel -> TYPE_LABEl
        }
    }
}
