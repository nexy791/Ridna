package com.ribsky.ridna.model.calendar

import androidx.recyclerview.widget.DiffUtil

data class CalendarModel(
    val date: Long,
    var color: Int? = null,
    val day: String,
    val isToday: Boolean,
    val isCurrentMonth: Boolean
) {

    companion object DiffCallback : DiffUtil.ItemCallback<CalendarModel>() {
        override fun areItemsTheSame(oldItem: CalendarModel, newItem: CalendarModel): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: CalendarModel, newItem: CalendarModel): Boolean =
            oldItem.date == newItem.date
    }
}
