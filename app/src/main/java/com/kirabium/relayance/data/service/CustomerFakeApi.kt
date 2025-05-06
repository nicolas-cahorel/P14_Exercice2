package com.kirabium.relayance.data.service

import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import java.util.Date

class CustomerFakeApi : CustomerApi {

    companion object {
        /**
         * Returns a date `monthsBack` months ago from the current date.
         */
        fun generateDateMonthsAgo(monthsBack: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -monthsBack)
            return calendar.time
        }
    }

    override fun getCustomers(): Flow<List<Customer>> = flow {
        val customers = listOf(
            Customer(1, "Alice Wonderland", "alice@example.com", generateDateMonthsAgo(12)),
            Customer(2, "Bob Builder", "bob@example.com", generateDateMonthsAgo(6)),
            Customer(3, "Charlie Chocolate", "charlie@example.com", generateDateMonthsAgo(3)),
            Customer(4, "Diana Dream", "diana@example.com", generateDateMonthsAgo(1)),
            Customer(5, "Evan Escape", "evan@example.com", generateDateMonthsAgo(0))
        )
        emit(customers)
    }

}