package com.kirabium.relayance.data.service

import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.Calendar
import java.util.Date

/**
 * A fake implementation of the [CustomerApi] interface used for testing or prototyping purposes.
 * This class simulates network calls by providing a static list of customers and in-memory operations.
 */
class CustomerFakeApi : CustomerApi {

    companion object {
        /**
         * Generates a [Date] object representing a moment in time that is
         * `monthsBack` months before the current date.
         *
         * @param monthsBack The number of months to subtract from the current date.
         * @return A [Date] object representing the calculated past date.
         */
        fun generateDateMonthsAgo(monthsBack: Int): Date {
            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, -monthsBack)
            return calendar.time
        }
    }

    /**
     * Internal mutable list simulating stored customer data.
     */
    private var _customers: MutableList<Customer> = mutableListOf(
        Customer(1, "Alice Wonderland", "alice@example.com", generateDateMonthsAgo(12)),
        Customer(2, "Bob Builder", "bob@example.com", generateDateMonthsAgo(6)),
        Customer(3, "Charlie Chocolate", "charlie@example.com", generateDateMonthsAgo(3)),
        Customer(4, "Diana Dream", "diana@example.com", generateDateMonthsAgo(1)),
        Customer(5, "Evan Escape", "evan@example.com", generateDateMonthsAgo(0))
    )

    /**
     * Returns a flow that emits the current list of customers.
     *
     * @return A [Flow] emitting a list of [Customer] instances.
     */
    override fun getCustomers(): Flow<List<Customer>> = flow {
        emit(_customers)
    }

    /**
     * Adds a new customer to the internal list and emits `true` upon success.
     *
     * @param name The name of the customer to be added.
     * @param email The email of the customer to be added.
     * @return A [Flow] emitting `true` when the customer is successfully added.
     */
    override fun addCustomer(name: String, email: String): Flow<Boolean> = flow {
        val newCustomer = Customer(
            name = name,
            email = email,
            createdAt = Calendar.getInstance().time,
            id = _customers.maxOf { it.id } + 1
        )
        _customers.add(newCustomer)
        emit(true)
    }

}