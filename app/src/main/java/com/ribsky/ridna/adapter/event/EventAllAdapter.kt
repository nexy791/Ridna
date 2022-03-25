package com.ribsky.ridna.adapter.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.data.model.event.EventApiModel
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.ridna.R
import com.ribsky.ridna.adapter.DiffCallbackEvent
import com.ribsky.ridna.databinding.ItemEventAllBinding
import com.ribsky.ridna.utils.ext.DateExtension.Companion.format

class EventAllAdapter(private val callback: Callback) :
    ListAdapter<BaseEventModel, RecyclerView.ViewHolder>(DiffCallbackEvent) {

    fun interface Callback {
        fun onClick(id: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        EventHolder.create(parent)

    override fun getItemViewType(position: Int): Int = R.layout.item_event_list

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as EventHolder).bind(getItem(position), callback)
    }

    private class EventHolder(private val binding: ItemEventAllBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BaseEventModel, callback: Callback) {
            binding.apply {
                root.setOnClickListener {
                    callback.onClick((item as EventApiModel).uid)
                }
                tvTitle.text = item.name
                tvDate.text = item.date.format
            }
        }

        companion object {
            fun create(parent: ViewGroup): EventHolder {
                val binding = ItemEventAllBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return EventHolder(binding)
            }
        }
    }
}
