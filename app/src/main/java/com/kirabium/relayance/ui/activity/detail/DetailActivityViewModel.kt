package com.kirabium.relayance.ui.activity.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.repository.CustomerResult
import com.kirabium.relayance.ui.activity.detail.DetailActivity.Companion.EXTRA_CUSTOMER_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the UI state of [DetailActivity].
 *
 * It retrieves and exposes the details of a customer identified by an ID passed via [SavedStateHandle].
 * The state is exposed as a [StateFlow] of [DetailActivityState] to reflect loading, success, or error states.
 *
 * @property customerRepository Repository used to fetch customer data.
 * @property savedStateHandle Provides access to saved state, including the customer ID.
 */
@HiltViewModel
class DetailActivityViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _detailActivityState =
        MutableStateFlow<DetailActivityState>(DetailActivityState.Loading)
    val detailActivityState: StateFlow<DetailActivityState> get() = _detailActivityState

    init {
        val customerId: Int = savedStateHandle[EXTRA_CUSTOMER_ID] ?: -1

        if (customerId == -1) {
            _detailActivityState.value =
                DetailActivityState.DisplayErrorMessage("Invalid customer ID")
        } else {
            viewModelScope.launch {
                customerRepository.getCustomers().collect { result ->
                    _detailActivityState.value = when (result) {
                        is CustomerResult.GetCustomersSuccess -> {
                            val customer = result.customers.find { it.id == customerId }
                            if (customer != null) {
                                DetailActivityState.DisplayCustomer(customer)
                            } else {
                                DetailActivityState.DisplayErrorMessage("Customer not found")
                            }
                        }

                        is CustomerResult.GetCustomersEmpty -> DetailActivityState.DisplayErrorMessage(
                            "No customers available"
                        )

                        is CustomerResult.GetCustomersError -> DetailActivityState.DisplayErrorMessage(
                            result.errorMessage
                        )

                        else -> DetailActivityState.DisplayErrorMessage("An error occurred")
                    }
                }
            }
        }
    }

}
