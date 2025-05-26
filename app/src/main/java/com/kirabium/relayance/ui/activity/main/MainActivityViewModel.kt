package com.kirabium.relayance.ui.activity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.repository.CustomerResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _mainActivityState =
        MutableStateFlow<MainActivityState>(MainActivityState.Loading)
    val mainActivityState: StateFlow<MainActivityState> get() = _mainActivityState

    init {
        fetchData()
    }

    fun fetchData () {
        viewModelScope.launch {
            customerRepository.getCustomers().collect { result ->
                _mainActivityState.value = when (result) {
                    is CustomerResult.GetCustomersSuccess -> MainActivityState.DisplayCustomers(result.customers)
                    is CustomerResult.GetCustomersEmpty -> MainActivityState.NoCustomerToDisplay("No customer to display")
                    is CustomerResult.GetCustomersError -> MainActivityState.DisplayErrorMessage(result.errorMessage)
                    else -> MainActivityState.DisplayErrorMessage("An error has occurred")
                }
            }
        }
    }
}