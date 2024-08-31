package com.ribsky.ridna.ui.view.calendar

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ribsky.ridna.R
import com.ribsky.ridna.databinding.ItemCalendarDayBinding
import com.ribsky.ridna.model.calendar.CalendarModel
import java.util.*

class CalendarAdapter(private val callback: CalendarCallback) :
    ListAdapter<CalendarModel, CalendarAdapter.CalendarViewHolder>(CalendarModel.DiffCallback) {

    inner class SelectedItem(
        var position: Int? = null,
        var model: CalendarModel? = null
    )

    private var currentDate: Date = Date()
    private var selectedItem: SelectedItem = SelectedItem()

    fun updateCurrentDate(date: Date) {
        currentDate = date
    }

    fun interface CalendarCallback {
        fun onClick(date: Long)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val binding = ItemCalendarDayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val item = getItem(position)
        if (item is CalendarModel) {
            holder.bind(item)
        }
    }

    inner class CalendarViewHolder(private val binding: ItemCalendarDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CalendarModel) {
            binding.apply {
                root.setOnClickListener {
                    callback.onClick(item.date)

                    val previousPosition = selectedItem.position
                    selectedItem.apply {
                        position = bindingAdapterPosition
                        model = item
                    }

                    selectedItem.position?.let { pos -> notifyItemChanged(pos) }
                    previousPosition?.let { pos -> notifyItemChanged(pos) }
                }

                tvDay.text = item.day

                if (item.color != null) {
                    icDay.setBackgroundColor(item.color!!)
                }

                if (item == selectedItem.model) {
                    ivPick.setImageResource(R.drawable.circle_border)
                } else {
                    ivPick.setImageDrawable(null)
                }

                if (item.isToday) {
                    ivPick.setImageResource(R.drawable.circle)
                }
                if (!item.isCurrentMonth) {
                    tvDay.alpha = 0.3f
                }
            }
        }
    }
}
