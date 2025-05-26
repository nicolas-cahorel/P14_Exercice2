package com.kirabium.relayance.ui.activity.add

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
class AddCustomerActivityViewModel @Inject constructor(
    private val customerRepository: CustomerRepository
) : ViewModel() {

    private val _addCustomerActivityState =
        MutableStateFlow<AddCustomerActivityState>(AddCustomerActivityState.Loading)
    val addCustomerActivityState: StateFlow<AddCustomerActivityState> get() = _addCustomerActivityState

    private var isNameEmpty: Boolean = true
    private var isEmailEmpty: Boolean = true
    private var isEmailFormatCorrect: Boolean = false

    fun onInputChanged(newName: String, newEmail: String) {
        validateName(newName)
        validateEmail(newEmail)

        if (!isNameEmpty && !isEmailEmpty && isEmailFormatCorrect) {
            _addCustomerActivityState.value = AddCustomerActivityState.ValidInput
        } else {
            _addCustomerActivityState.value = AddCustomerActivityState.InvalidInput(isNameEmpty,isEmailEmpty,isEmailFormatCorrect)
        }

    }

    private fun validateName(newName: String) {
        isNameEmpty = newName.isEmpty()
    }

    private fun validateEmail(newEmail: String) {
        if (newEmail.isNotEmpty()) {
            val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
            isEmailFormatCorrect = newEmail.trim().matches(emailPattern.toRegex())
            isEmailEmpty = false
        } else {
            isEmailEmpty = true
        }
    }

    fun addCustomer(newName: String,newEmail: String) {
        viewModelScope.launch {
            customerRepository.addCustomer(newName, newEmail).collect { result ->
                _addCustomerActivityState.value = when (result) {
                    is CustomerResult.AddCustomerSuccess -> AddCustomerActivityState.AddCustomerSuccess
                    is CustomerResult.AddCustomerError -> AddCustomerActivityState.AddCustomerError(result.errorMessage)
                    else -> AddCustomerActivityState.AddCustomerError("An error has occurred")
                }
            }
        }
    }

}