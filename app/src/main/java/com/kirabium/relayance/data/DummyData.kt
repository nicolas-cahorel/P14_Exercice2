package com.kirabium.relayance.data

import com.kirabium.relayance.domain.model.Customer
import java.util.Calendar
import java.util.Date

/**
 * Provides dummy customer data for development, testing, or UI prototyping.
 */
object DummyData {

    /**
     * Generates a [Date] representing a point in time `monthsBack` months before the current date.
     *
     * @param monthsBack The number of months to subtract from the current date.
     * @return A [Date] object representing the past date.
     */
    fun generateDate(monthsBack: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -monthsBack)
        return calendar.time
    }

    /**
     * A predefined list of sample [Customer] objects for use as test data.
     */
    val customers = listOf(
        Customer(1, "Alice Wonderland", "alice@example.com", generateDate(12)),
        Customer(2, "Bob Builder", "bob@example.com", generateDate(6)),
        Customer(3, "Charlie Chocolate", "charlie@example.com", generateDate(3)),
        Customer(4, "Diana Dream", "diana@example.com", generateDate(1)),
        Customer(5, "Evan Escape", "evan@example.com", generateDate(0)),
    )
}