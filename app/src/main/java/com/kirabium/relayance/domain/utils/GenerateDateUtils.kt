package com.kirabium.relayance.domain.utils

import java.util.Calendar
import java.util.Date

class GenerateDateUtils {

    /**
     * Returns a date `monthsBack` months ago from the current date.
     */
    fun generateDateMonthsAgo(monthsBack: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -monthsBack)
        return calendar.time
    }
}