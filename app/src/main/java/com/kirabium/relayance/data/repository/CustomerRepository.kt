package com.kirabium.relayance.data.repository

import com.kirabium.relayance.data.service.CustomerApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CustomerRepository @Inject constructor(
    private val customerApi: CustomerApi
){

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
                emit(CustomerResult.GetCustomersError(exception.message ?: "Error while fetching customers"))
            }
    }

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