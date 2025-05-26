package com.kirabium.relayance.data.repository

import com.kirabium.relayance.domain.model.Customer

sealed class CustomerResult {

    data class GetCustomersSuccess(val customers: List<Customer>) : CustomerResult()

    data class GetCustomersError(val errorMessage: String) : CustomerResult()

    data object GetCustomersEmpty : CustomerResult()

    data object AddCustomerSuccess : CustomerResult()

    data class AddCustomerError(val errorMessage: String) : CustomerResult()

}