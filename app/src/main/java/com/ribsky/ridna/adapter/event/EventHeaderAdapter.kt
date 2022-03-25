package com.ribsky.ridna.adapter.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.ridna.R
import com.ribsky.ridna.adapter.DiffCallbackEvent
import com.ribsky.ridna.databinding.ItemEventHeaderBinding

class EventHeaderAdapter :
    ListAdapter<BaseEventModel, RecyclerView.ViewHolder>(DiffCallbackEvent) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DayHolder.create(parent)

    override fun getItemViewType(position: Int): Int = R.layout.item_event_header
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as DayHolder).bind(getItem(position))
    }

    private class DayHolder(private val binding: ItemEventHeaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BaseEventModel) {
            binding.apply {
                tvTitle.text = item.name
                tvDescription.text = item.description
            }
        }

        companion object {
            fun create(parent: ViewGroup): DayHolder {
                val binding = ItemEventHeaderBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return DayHolder(binding)
            }
        }
    }
}
