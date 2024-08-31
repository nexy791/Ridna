package com.ribsky.data.utils.lib

import java.util.Calendar
import java.util.Date

operator fun Date.plus(duration: TimeInterval): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(duration.unit, duration.value)
    return calendar.time
}

operator fun Date.minus(duration: TimeInterval): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(duration.unit, -duration.value)
    return calendar.time
}

val Date.beginningOfMonth: Date
    get() = with(day = 1, hour = 0, minute = 0, second = 0, millisecond = 0)

fun Date.with(
    year: Int = -1,
    month: Int = -1,
    day: Int = -1,
    hour: Int = -1,
    minute: Int = -1,
    second: Int = -1,
    millisecond: Int = -1
): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    if (year > -1) calendar.set(Calendar.YEAR, year)
    if (month > 0) calendar.set(Calendar.MONTH, month - 1)
    if (day > 0) calendar.set(Calendar.DATE, day)
    if (hour > -1) calendar.set(Calendar.HOUR_OF_DAY, hour)
    if (minute > -1) calendar.set(Calendar.MINUTE, minute)
    if (second > -1) calendar.set(Calendar.SECOND, second)
    if (millisecond > -1) calendar.set(Calendar.MILLISECOND, millisecond)
    return calendar.time
}

fun Date.with(weekday: Int = -1): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    if (weekday > -1) calendar.set(Calendar.WEEK_OF_MONTH, weekday)
    return calendar.time
}
