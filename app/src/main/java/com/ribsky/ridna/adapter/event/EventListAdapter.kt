package com.ribsky.ridna.adapter.event

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.cesarferreira.tempo.Tempo
import com.ribsky.data.model.event.EventApiModel
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.ridna.R
import com.ribsky.ridna.adapter.DiffCallbackEvent
import com.ribsky.ridna.databinding.ItemEventListBinding
import com.ribsky.ridna.utils.ext.DateExtension.Companion.date
import com.ribsky.ridna.utils.ext.DateExtension.Companion.diffInDays
import com.ribsky.ridna.utils.ext.DateExtension.Companion.format

class EventListAdapter(private val callback: Callback) :
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

    private class EventHolder(private val binding: ItemEventListBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val time = Tempo.now.time.date

        fun bind(item: BaseEventModel, callback: Callback) {

            binding.apply {
                root.setOnClickListener {
                    callback.onClick((item as EventApiModel).uid)
                }
                tvTitle.text = item.name
                tvDate.text = item.date.format
                checkBox.isChecked = time >= item.date
                tvTimer.text = root.context.diffInDays(time, item.date)
            }
        }

        companion object {
            fun create(parent: ViewGroup): EventHolder {
                val binding = ItemEventListBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return EventHolder(binding)
            }
        }
    }
}
