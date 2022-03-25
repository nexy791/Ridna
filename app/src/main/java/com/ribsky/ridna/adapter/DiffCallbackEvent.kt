package com.ribsky.ridna.adapter

import androidx.recyclerview.widget.DiffUtil
import com.ribsky.domain.model.event.BaseEventModel

object DiffCallbackEvent : DiffUtil.ItemCallback<BaseEventModel>() {
    override fun areItemsTheSame(
        oldItem: BaseEventModel,
        newItem: BaseEventModel
    ): Boolean =
        oldItem == newItem

    override fun areContentsTheSame(
        oldItem: BaseEventModel,
        newItem: BaseEventModel
    ): Boolean =
        oldItem.name == newItem.name
}
