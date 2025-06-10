package com.kirabium.relayance.extension

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

/**
 * Utility class containing date-related extensions.
 */
class DateExt {

    companion object {

        /**
         * Extension function for [Date] that converts a Date object
         * to a human-readable string in the format "dd/MM/yyyy".
         *
         * @receiver Date The date to format.
         * @return A string representation of the date in "dd/MM/yyyy" format.
         */
        fun Date.toHumanDate(): String {
            val calendar = Calendar.getInstance()
            calendar.time = this
            val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            return dateFormat.format(calendar.time)
        }
    }

}