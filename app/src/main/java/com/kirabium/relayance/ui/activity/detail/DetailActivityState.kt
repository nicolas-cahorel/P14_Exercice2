package com.kirabium.relayance.ui.activity.detail

import com.kirabium.relayance.domain.model.Customer

sealed class DetailActivityState {

    data object Loading : DetailActivityState()

    data class DisplayCustomer(
        val customer: Customer
    ) : DetailActivityState()

    data class DisplayErrorMessage(
        val stateMessage: String
    ) : DetailActivityState()

}