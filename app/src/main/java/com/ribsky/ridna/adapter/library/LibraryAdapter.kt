package com.ribsky.ridna.adapter.library

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.ridna.databinding.ItemLibraryBinding
import com.ribsky.ridna.model.library.LibraryModel

class LibraryAdapter(private val onClickListener: Callback) :
    ListAdapter<LibraryModel, RecyclerView.ViewHolder>(LibraryModel.Companion.DiffCallback) {

    fun interface Callback {
        fun onClick(link: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return LibraryHolder.create(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        (holder as LibraryHolder).bind(item, onClickListener)
    }

    private class LibraryHolder(private val binding: ItemLibraryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: LibraryModel, callback: Callback) {
            binding.apply {
                root.setOnClickListener {
                    callback.onClick(item.link)
                }
                tvTitle.text = item.title
                tvDescription.text = item.description
            }
        }

        companion object {
            fun create(parent: ViewGroup): LibraryHolder {
                val binding = ItemLibraryBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                return LibraryHolder(binding)
            }
        }
    }
}
