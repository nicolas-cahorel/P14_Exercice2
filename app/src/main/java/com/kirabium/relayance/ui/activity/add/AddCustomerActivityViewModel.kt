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

/**
 * ViewModel responsible for managing the UI state and business logic
 * of the AddCustomerActivity.
 *
 * It handles input validation and coordinates adding a new customer
 * through the CustomerRepository.
 *
 * @property customerRepository The repository used to add customers.
 */
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

    /**
     * Called whenever the input fields for name or email change.
     * Validates the inputs and updates the UI state accordingly.
     *
     * @param newName The current value of the name input field.
     * @param newEmail The current value of the email input field.
     */
    fun onInputChanged(newName: String, newEmail: String) {
        validateName(newName)
        validateEmail(newEmail)

        if (!isNameEmpty && !isEmailEmpty && isEmailFormatCorrect) {
            _addCustomerActivityState.value = AddCustomerActivityState.ValidInput
        } else {
            _addCustomerActivityState.value = AddCustomerActivityState.InvalidInput(
                isNameEmpty,
                isEmailEmpty,
                isEmailFormatCorrect
            )
        }

    }

    /**
     * Validates whether the given name is empty.
     *
     * @param newName The name string to validate.
     */
    private fun validateName(newName: String) {
        isNameEmpty = newName.isEmpty()
    }

    /**
     * Validates whether the given email is empty and matches the email pattern.
     *
     * @param newEmail The email string to validate.
     */
    private fun validateEmail(newEmail: String) {
        if (newEmail.isNotEmpty()) {
            val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
            isEmailFormatCorrect = newEmail.trim().matches(emailPattern.toRegex())
            isEmailEmpty = false
        } else {
            isEmailEmpty = true
        }
    }

    /**
     * Attempts to add a new customer with the given name and email.
     * Updates the UI state with success or error based on the repository result.
     *
     * @param newName The name of the new customer.
     * @param newEmail The email of the new customer.
     */
    fun addCustomer(newName: String, newEmail: String) {
        viewModelScope.launch {
            customerRepository.addCustomer(newName, newEmail).collect { result ->
                _addCustomerActivityState.value = when (result) {
                    is CustomerResult.AddCustomerSuccess -> AddCustomerActivityState.AddCustomerSuccess
                    is CustomerResult.AddCustomerError -> AddCustomerActivityState.AddCustomerError(
                        result.errorMessage
                    )

                    else -> AddCustomerActivityState.AddCustomerError("An error has occurred")
                }
            }
        }
    }

}