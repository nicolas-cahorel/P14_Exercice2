package com.kirabium.relayance.ui.activity.add

import androidx.lifecycle.ViewModel
import com.kirabium.relayance.data.repository.CustomerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class AddCustomerActivityViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _addCustomerActivityState =
        MutableStateFlow<AddCustomerActivityState>(AddCustomerActivityState.Loading)
    val addCustomerActivityState: StateFlow<AddCustomerActivityState> get() = _addCustomerActivityState

    init {
        // TODO :
    }

}