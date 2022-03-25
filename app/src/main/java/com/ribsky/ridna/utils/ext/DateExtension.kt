package com.ribsky.ridna.utils.ext

import android.content.Context
import com.cesarferreira.tempo.Tempo
import com.ribsky.ridna.R
import com.ribsky.ridna.utils.ext.ViewExtension.Companion.string
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class DateExtension {

    companion object {

        val Long.date: Long
            get() {
                val cal = Calendar.getInstance().apply {
                    timeInMillis = this@date
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }
                return cal.time.time
            }

        fun Context.diffInDays(dateNow: Long, dateNext: Long): String {
            val msDiff: Long = dateNext - dateNow
            val daysDiff = TimeUnit.MILLISECONDS.toDays(msDiff).toInt()

            if (daysDiff < -1)
                return dateNext.format

            return when (daysDiff) {
                -1 -> string(R.string.day_yesterday)
                0 -> string(R.string.day_today)
                1 -> string(R.string.day_tomorrow)
                7 -> string(R.string.day_week)
                30, 31 -> string(R.string.day_month)
                else -> string(R.string.day_in_day).format(daysDiff)
            }
        }

        val Long.format: String
            get() = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(this)

        val Long.daysBetween: Int
            get() = TimeUnit.MILLISECONDS.toDays(Tempo.now.time.date - this).toInt()
    }
}
