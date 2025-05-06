package com.kirabium.relayance.ui.activity.main

import com.kirabium.relayance.domain.model.Customer

sealed class MainActivityState {

    data object Loading : MainActivityState()

    data class DisplayCustomers(
        val customers: List<Customer>
    ) : MainActivityState()

    data class NoCustomerToDisplay(
        val stateMessage: String
    ) : MainActivityState()

    data class DisplayErrorMessage(
        val stateMessage: String
    ) : MainActivityState()

}