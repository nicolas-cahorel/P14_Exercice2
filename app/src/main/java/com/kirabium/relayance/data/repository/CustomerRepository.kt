package com.kirabium.relayance.data.repository

import com.kirabium.relayance.data.service.CustomerApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository class responsible for managing customer-related operations.
 * It communicates with the remote [CustomerApi] and returns the results wrapped in [CustomerResult].
 *
 * This class is marked as [Singleton] to ensure a single instance is used across the application.
 *
 * @property customerApi The API interface for performing customer-related network operations.
 */
@Singleton
class CustomerRepository @Inject constructor(
    private val customerApi: CustomerApi
) {

    /**
     * Retrieves the list of customers from the remote data source.
     *
     * @return A [Flow] emitting a [CustomerResult] depending on the outcome:
     * - [CustomerResult.GetCustomersSuccess] if the list is retrieved successfully and not empty
     * - [CustomerResult.GetCustomersEmpty] if the list is empty
     * - [CustomerResult.GetCustomersError] if an exception occurs
     */
    fun getCustomers(): Flow<CustomerResult> {
        return customerApi.getCustomers()
            .map { customers ->
                if (customers.isEmpty()) {
                    CustomerResult.GetCustomersEmpty
                } else {
                    CustomerResult.GetCustomersSuccess(customers)
                }
            }
            .catch { exception ->
                emit(
                    CustomerResult.GetCustomersError(
                        exception.message ?: "Error while fetching customers"
                    )
                )
            }
    }

    /**
     * Adds a new customer with the provided name and email.
     *
     * @param name The name of the customer to add.
     * @param email The email of the customer to add.
     * @return A [Flow] emitting a [CustomerResult] depending on the outcome:
     * - [CustomerResult.AddCustomerSuccess] if the customer is added successfully
     * - [CustomerResult.AddCustomerError] if the addition fails or an exception occurs
     */
    fun addCustomer(name: String, email: String): Flow<CustomerResult> {
        return customerApi.addCustomer(name, email)
            .map { success ->
                if (success) {
                    CustomerResult.AddCustomerSuccess
                } else {
                    CustomerResult.AddCustomerError("Failed to add customer")
                }
            }
            .catch { exception ->
                emit(CustomerResult.AddCustomerError(exception.message ?: "Unknown error"))
            }
    }

}