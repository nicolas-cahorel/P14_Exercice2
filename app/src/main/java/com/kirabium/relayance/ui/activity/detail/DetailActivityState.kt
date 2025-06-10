package com.kirabium.relayance.ui.activity.detail

import com.kirabium.relayance.domain.model.Customer

/**
 * Represents the different UI states for the DetailActivity.
 */
sealed class DetailActivityState {

    /**
     * State indicating that the customer data is currently loading.
     */
    data object Loading : DetailActivityState()

    /**
     * State representing successful loading of a customer's details.
     *
     * @property customer The [Customer] object to display.
     */
    data class DisplayCustomer(
        val customer: Customer
    ) : DetailActivityState()

    /**
     * State representing an error that occurred when trying to load or display customer details.
     *
     * @property stateMessage The error message to show to the user.
     */
    data class DisplayErrorMessage(
        val stateMessage: String
    ) : DetailActivityState()

}