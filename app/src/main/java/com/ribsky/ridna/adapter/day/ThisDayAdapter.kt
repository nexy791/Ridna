package com.ribsky.ridna.adapter.day

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.AlignItems
import com.google.android.flexbox.FlexboxLayoutManager
import com.ribsky.domain.model.event.BaseEventModel
import com.ribsky.ridna.adapter.DiffCallbackEvent
import com.ribsky.ridna.databinding.ItemThisDayBinding

class ThisDayAdapter(private val onClickListener: Callback) :
    ListAdapter<BaseEventModel, RecyclerView.ViewHolder>(DiffCallbackEvent) {

    interface Callback {
        fun onClick(event: BaseEventModel)
        fun onShare(view: View, event: BaseEventModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        DayHolder.create(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as DayHolder).bind(item, onClickListener)
    }

    private class DayHolder(private val binding: ItemThisDayBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: BaseEventModel, callback: Callback) {

            itemView.layoutParams.updateToFlexBox()

            binding.apply {
                btnOpen.setOnClickListener {
                    callback.onClick(item)
                }
                btnShare.setOnClickListener {
                    callback.onShare(screenshotContainer, item)
                }
                screenshotContainer.setOnClickListener {
                    callback.onClick(item)
                }
                tvDays.text = item.name
            }
        }

        companion object {
            fun create(parent: ViewGroup): DayHolder {
                val binding = ItemThisDayBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return DayHolder(binding)
            }
        }

        private fun ViewGroup.LayoutParams.updateToFlexBox() {
            (this as? FlexboxLayoutManager.LayoutParams)?.let {
                it.flexShrink = 0.0f
                it.alignSelf = AlignItems.FLEX_START
            }
        }
    }
}
