package com.kirabium.relayance.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.repository.CustomerResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing and exposing the state of the [MainActivity].
 *
 * This ViewModel fetches customer data from the [CustomerRepository] and updates the UI state accordingly.
 * It handles loading, successful data retrieval, empty data, and error scenarios.
 *
 * @property customerRepository Repository used to fetch customer data.
 * @property dispatcher The [CoroutineDispatcher] used for coroutine execution. Defaults to [Dispatchers.IO].
 */
@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val customerRepository: CustomerRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel() {

    private val _mainActivityState =
        MutableStateFlow<MainActivityState>(MainActivityState.Loading)
    val mainActivityState: StateFlow<MainActivityState> get() = _mainActivityState

    init {
        fetchData()
    }

    /**
     * Fetches the list of customers asynchronously from the repository.
     *
     * Updates [_mainActivityState] with the appropriate [MainActivityState] based on
     * the repository result: success, empty list, or error.
     */
    fun fetchData() {
        viewModelScope.launch(dispatcher) {
            customerRepository.getCustomers().collect { result ->
                _mainActivityState.value = when (result) {
                    is CustomerResult.GetCustomersSuccess -> MainActivityState.DisplayCustomers(
                        result.customers
                    )

                    is CustomerResult.GetCustomersEmpty -> MainActivityState.NoCustomerToDisplay("No customer to display")
                    is CustomerResult.GetCustomersError -> MainActivityState.DisplayErrorMessage(
                        result.errorMessage
                    )

                    else -> MainActivityState.DisplayErrorMessage("An error has occurred")
                }
            }
        }
    }
}