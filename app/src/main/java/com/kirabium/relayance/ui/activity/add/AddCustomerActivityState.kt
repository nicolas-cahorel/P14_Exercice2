package com.kirabium.relayance.ui.activity.add

/**
 * Represents the various UI states of the AddCustomerActivity.
 * Used by the ViewModel to communicate the current status of the add customer form and operation.
 */
sealed class AddCustomerActivityState {

    /**
     * Indicates that a customer is currently being added (loading state).
     * Used to disable UI elements and show progress indicators.
     */
    data object Loading : AddCustomerActivityState()

    /**
     * Indicates that the user input is invalid.
     *
     * @param isNameEmpty true if the name field is empty
     * @param isEmailEmpty true if the email field is empty
     * @param isEmailFormatCorrect true if the email has a valid format
     */
    data class InvalidInput(
        val isNameEmpty: Boolean,
        val isEmailEmpty: Boolean,
        val isEmailFormatCorrect: Boolean
    ) : AddCustomerActivityState()

    /**
     * Indicates that the user input is valid and ready to be submitted.
     */
    data object ValidInput : AddCustomerActivityState()

    /**
     * Indicates that the customer was successfully added.
     * Used to trigger the UI to finish the activity and return a result.
     */
    data object AddCustomerSuccess : AddCustomerActivityState()

    /**
     * Indicates that an error occurred while trying to add a customer.
     *
     * @param errorMessage the message to display describing the error
     */
    data class AddCustomerError(val errorMessage: String) : AddCustomerActivityState()

}