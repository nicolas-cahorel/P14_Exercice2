package com.kirabium.relayance.data.service

import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.flow.Flow

interface CustomerApi {

    fun getCustomers() : Flow<List<Customer>>

}