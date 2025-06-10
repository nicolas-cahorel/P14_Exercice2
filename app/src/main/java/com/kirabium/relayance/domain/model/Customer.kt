package com.kirabium.relayance.domain.model

import java.util.Calendar
import java.util.Date

/**
 * Data class representing a customer entity.
 *
 * @property id Unique identifier for the customer.
 * @property name The name of the customer.
 * @property email The email address of the customer.
 * @property createdAt The date when the customer was created.
 */
data class Customer(val id: Int, val name: String, val email: String, val createdAt: Date) {

    /**
     * Checks whether the customer is considered "new".
     * A customer is considered new if they were created within the last 3 months.
     *
     * @return True if the customer was created less than 3 months ago, false otherwise.
     */
    fun isNewCustomer(): Boolean {
        val today = Calendar.getInstance()
        val createdAtCalendar = Calendar.getInstance().apply {
            time = createdAt
        }
        today.add(Calendar.MONTH, -3)
        return !createdAtCalendar.before(today)
    }

}
