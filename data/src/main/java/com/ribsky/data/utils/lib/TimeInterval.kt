package com.ribsky.data.utils.lib

import java.util.Calendar
import java.util.Date
import java.util.Objects

class TimeInterval(internal val unit: Int, internal val value: Int) {
    val ago = calculate(from = Date(), value = -value)

    val forward = calculate(from = Date(), value = value)

    private fun calculate(from: Date, value: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = from
        calendar.add(unit, value)
        return calendar.time
    }

    override fun hashCode() = Objects.hashCode(unit) * Objects.hashCode(value)

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is TimeInterval) {
            return false
        }
        return unit == other.unit && value == other.value
    }
}