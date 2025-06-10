package com.kirabium.relayance.data.service

import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow

/**
 * API interface for managing customer-related operations.
 * This service provides methods to retrieve and add customers via network or local data source.
 */
interface CustomerApi {

    /**
     * Retrieves a flow emitting the list of customers.
     *
     * @return A [Flow] emitting a [List] of [Customer] objects.
     */
    fun getCustomers(): Flow<List<Customer>>

    /**
     * Adds a new customer with the specified name and email.
     *
     * @param name The name of the customer to be added.
     * @param email The email address of the customer to be added.
     * @return A [Flow] emitting `true` if the customer was successfully added, `false` otherwise.
     */
    fun addCustomer(name: String, email: String): Flow<Boolean>

}