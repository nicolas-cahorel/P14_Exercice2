package com.kirabium.relayance.ui.activity.add

sealed class AddCustomerActivityState {

    data object Loading : AddCustomerActivityState()

    data object DisplayInputScreen : AddCustomerActivityState()

}