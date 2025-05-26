package com.kirabium.relayance.ui.activity.add

sealed class AddCustomerActivityState {

    data object Loading : AddCustomerActivityState()

    data class InvalidInput(val isNameEmpty: Boolean, val isEmailEmpty: Boolean, val isEmailFormatCorrect: Boolean) : AddCustomerActivityState()

    data object ValidInput : AddCustomerActivityState()

    data object AddCustomerSuccess : AddCustomerActivityState()

    data class AddCustomerError(val errorMessage: String) : AddCustomerActivityState()

}