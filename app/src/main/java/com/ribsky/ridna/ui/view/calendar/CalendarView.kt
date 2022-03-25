package com.ribsky.ridna.ui.view.calendar

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.annotation.AttrRes
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cesarferreira.tempo.*
import com.google.android.material.textview.MaterialTextView
import com.ribsky.ridna.R
import com.ribsky.ridna.model.calendar.CalendarModel
import com.ribsky.ridna.model.calendar.DayModel
import com.ribsky.ridna.utils.ext.DateExtension.Companion.date
import com.ribsky.ridna.utils.ext.DateExtension.Companion.format
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import java.util.*

class CalendarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    interface Callback {
        fun onFilled(dates: LongRange)
        fun onClicked(date: Long)
    }

    private var currentDate: Date = Tempo.now
        set(value) {
            field = value
            adapter?.updateCurrentDate(value)
            fillCalendar()
        }

    private var _view: View? = null
    private var recyclerview: RecyclerView? = null
    private var adapter: CalendarAdapter? = null
    private var callback: Callback? = null

    private var ivBack: ImageView? = null
    private var ivNext: ImageView? = null
    private var tvMonth: MaterialTextView? = null
    private var tvCalendar: MaterialTextView? = null

    private var dates: MutableList<CalendarModel> = mutableListOf()
    private var months = listOf(
        context.string(R.string.month_0),
        context.string(R.string.month_1),
        context.string(R.string.month_2),
        context.string(R.string.month_3),
        context.string(R.string.month_4),
        context.string(R.string.month_5),
        context.string(R.string.month_6),
        context.string(R.string.month_7),
        context.string(R.string.month_8),
        context.string(R.string.month_9),
        context.string(R.string.month_10),
        context.string(R.string.month_11)
    )

    init {
        init(context)
    }

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    private fun init(context: Context) {
        _view = LayoutInflater.from(context).inflate(R.layout.layout_calendar, this)
        initIds()
        initViews()
    }

    fun updateEvents(list: List<DayModel>) {

        val newDates: List<CalendarModel> = dates.map { day ->
            list.firstOrNull { it.date == day.date }?.let {
                return@let day.apply {
                    color = it.color
                }
            } ?: day.apply {
                color = null
            }
        }

        recyclerview?.adapter = adapter?.apply {
            submitList(newDates)
        }
    }

    fun fillCalendar() {

        dates.clear()

        val calendar = Calendar.getInstance().apply {
            time = currentDate
        }

        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val daysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        "${months[currentMonth]}, $currentYear".also { tvMonth?.text = it }

        val beginningOfMonth = currentDate.beginningOfMonth
        var date = beginningOfMonth

        val offset = Calendar.getInstance().let {
            it.time = beginningOfMonth
            it.get(Calendar.DAY_OF_WEEK) - 2
        }

        for (i in offset downTo 1) {
            val _date = (beginningOfMonth - i.days).time.date

            val day = Calendar.getInstance().apply {
                timeInMillis = _date
            }.get(Calendar.DAY_OF_MONTH)

            val isToday = _date == Tempo.now.time.date
            dates.add(
                CalendarModel(
                    date = _date,
                    day = day.toString(),
                    isToday = isToday,
                    isCurrentMonth = false
                )
            )
        }

        for (i in 1..daysInMonth) {
            val _date = date.time.date

            val isToday = _date == Tempo.now.time.date
            dates.add(
                CalendarModel(
                    date = _date,
                    day = i.toString(),
                    isToday = isToday,
                    isCurrentMonth = true
                )
            )
            date += 1.day
        }

        recyclerview?.adapter = adapter?.apply {
            submitList(dates) {
                callback?.onFilled(
                    dates.first().date..dates.last().date
                )
            }
        }
    }

    private fun initIds() {
        recyclerview = findViewById(R.id.grid_calendar)

        ivBack = findViewById(R.id.iv_back)
        ivNext = findViewById(R.id.iv_next)
        tvMonth = findViewById(R.id.tv_month)

        tvCalendar = findViewById(R.id.tv_calendar)
    }

    private fun initViews() {

        adapter = CalendarAdapter { date ->
            callback?.onClicked(date)
        }

        ivBack?.setOnClickListener {
            currentDate -= 2.months
        }

        ivNext?.setOnClickListener {
            currentDate += 2.months
        }

        recyclerview?.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(context, 7)
            itemAnimator = null
            isNestedScrollingEnabled = false
        }
        tvCalendar?.text = context.string(R.string.today_date).format(Tempo.now.time.date.format)
    }
}
