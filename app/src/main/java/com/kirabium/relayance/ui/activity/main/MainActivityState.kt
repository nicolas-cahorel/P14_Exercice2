package com.kirabium.relayance.ui.activity.main

import com.kirabium.relayance.domain.model.Customer

/**
 * Represents the various UI states for the [MainActivity].
 *
 * This sealed class defines different states to reflect the current status
 * of customer data loading and display.
 */
sealed class MainActivityState {

    /**
     * Represents the loading state when customer data is being fetched.
     */
    data object Loading : MainActivityState()

    /**
     * Represents the state where a list of customers is available for display.
     *
     * @property customers The list of customers to display.
     */
    data class DisplayCustomers(
        val customers: List<Customer>
    ) : MainActivityState()

    /**
     * Represents the state when there are no customers to display.
     *
     * @property stateMessage The message describing this state.
     */
    data class NoCustomerToDisplay(
        val stateMessage: String
    ) : MainActivityState()

    /**
     * Represents the state when an error occurred and an error message must be shown.
     *
     * @property stateMessage The error message to display.
     */
    data class DisplayErrorMessage(
        val stateMessage: String
    ) : MainActivityState()

}