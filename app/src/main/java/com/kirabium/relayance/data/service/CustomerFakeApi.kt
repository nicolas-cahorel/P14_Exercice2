package com.kirabium.relayance.data.service

import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.domain.utils.GenerateDateUtils
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CustomerFakeApi (
    private val dateUtils: GenerateDateUtils
) : CustomerApi {

    override fun getCustomers(): Flow<List<Customer>> = flow {
        val customers = listOf(
            Customer(1, "Alice Wonderland", "alice@example.com", dateUtils.generateDateMonthsAgo(12)),
            Customer(2, "Bob Builder", "bob@example.com", dateUtils.generateDateMonthsAgo(6)),
            Customer(3, "Charlie Chocolate", "charlie@example.com", dateUtils.generateDateMonthsAgo(3)),
            Customer(4, "Diana Dream", "diana@example.com", dateUtils.generateDateMonthsAgo(1)),
            Customer(5, "Evan Escape", "evan@example.com", dateUtils.generateDateMonthsAgo(0))
        )
        emit(customers)
    }

}