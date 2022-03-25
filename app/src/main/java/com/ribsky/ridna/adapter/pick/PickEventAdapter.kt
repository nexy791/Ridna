package com.ribsky.ridna.adapter.pick

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.ridna.databinding.ItemEventBinding

class PickEventAdapter(private val onClickListener: Callback) :
    ListAdapter<String, RecyclerView.ViewHolder>(DiffCallback) {

    fun interface Callback {
        fun onClick(event: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return PickHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as PickHolder).bind(item, onClickListener)
    }

    private class PickHolder(private val binding: ItemEventBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: String, callback: Callback) {
            binding.apply {
                btnPick.setOnClickListener {
                    callback.onClick(item)
                }
                textView.text = item
            }
        }

        companion object {
            fun create(parent: ViewGroup): PickHolder {
                val binding = ItemEventBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return PickHolder(binding)
            }
        }
    }

    private object DiffCallback : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(
            oldItem: String,
            newItem: String
        ): Boolean =
            oldItem == newItem
    }
}
