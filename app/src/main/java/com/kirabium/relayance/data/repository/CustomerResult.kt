package com.kirabium.relayance.data.repository

import com.kirabium.relayance.domain.model.Customer

/**
 * Represents the possible outcomes of customer-related operations such as fetching or adding customers.
 */
sealed class CustomerResult {

    /**
     * Represents a successful result when retrieving a list of customers.
     *
     * @property customers The list of retrieved [Customer] objects.
     */
    data class GetCustomersSuccess(val customers: List<Customer>) : CustomerResult()

    /**
     * Represents an error that occurred while retrieving customers.
     *
     * @property errorMessage A descriptive message about the error.
     */
    data class GetCustomersError(val errorMessage: String) : CustomerResult()

    /**
     * Represents the result when the customer list is successfully retrieved but is empty.
     */
    data object GetCustomersEmpty : CustomerResult()

    /**
     * Represents a successful result when adding a new customer.
     */
    data object AddCustomerSuccess : CustomerResult()

    /**
     * Represents an error that occurred while adding a customer.
     *
     * @property errorMessage A descriptive message about the error.
     */
    data class AddCustomerError(val errorMessage: String) : CustomerResult()

}