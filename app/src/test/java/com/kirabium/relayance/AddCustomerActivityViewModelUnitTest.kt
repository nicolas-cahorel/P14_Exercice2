package com.kirabium.relayance

import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.repository.CustomerResult
import com.kirabium.relayance.ui.activity.add.AddCustomerActivityState
import com.kirabium.relayance.ui.activity.add.AddCustomerActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

/**
 * Unit tests for [AddCustomerActivityViewModel].
 *
 * These tests verify the behavior of input validation and customer addition
 * logic using mocked [CustomerRepository].
 *
 * Uses Kotlin coroutines test utilities to control coroutine dispatchers and execution.
 */
@ExperimentalCoroutinesApi
class AddCustomerActivityViewModelUnitTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockCustomerRepository: CustomerRepository

    private lateinit var viewModel: AddCustomerActivityViewModel

    /**
     * Setup method to initialize mocks and set the main dispatcher to [testDispatcher].
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = AddCustomerActivityViewModel(mockCustomerRepository)
    }

    /**
     * Cleanup method to reset the main dispatcher after tests.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Tests that when valid name and email inputs are provided,
     * the state is updated to [AddCustomerActivityState.ValidInput].
     */
    @Test
    fun onInputChanged_ReturnsValid() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = "alice@mail.com"
        // ACT
        viewModel.onInputChanged(fakeName, fakeEmail)
        // ASSERT
        val expectedState = AddCustomerActivityState.ValidInput
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests that an empty name input results in an invalid state
     * with the name error flag set.
     */
    @Test
    fun onInputChanged_NameEmpty_ReturnsInvalid() = runTest {
        // ARRANGE
        val fakeName = ""
        val fakeEmail = "alice@mail.com"
        // ACT
        viewModel.onInputChanged(fakeName, fakeEmail)
        // ASSERT
        val expectedState = AddCustomerActivityState.InvalidInput(true, false, true)
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests that an empty email input results in an invalid state
     * with the email error flag set.
     */
    @Test
    fun onInputChanged_EmailEmpty_ReturnsInvalid() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = ""
        // ACT
        viewModel.onInputChanged(fakeName, fakeEmail)
        // ASSERT
        val expectedState = AddCustomerActivityState.InvalidInput(false, true, false)
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests that an incorrectly formatted email input results in an invalid state
     * with no error flags set (assuming format validation logic).
     */
    @Test
    fun onInputChanged_EmailIncorrectFormat_ReturnsInvalid() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = "alice.mail.com"
        // ACT
        viewModel.onInputChanged(fakeName, fakeEmail)
        // ASSERT
        val expectedState = AddCustomerActivityState.InvalidInput(false, false, false)
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests that both empty name and email inputs result in an invalid state
     * with both error flags set.
     */
    @Test
    fun onInputChanged_NameAndEmailEmpty_ReturnsInvalid() = runTest {
        // ARRANGE
        val fakeName = ""
        val fakeEmail = ""
        // ACT
        viewModel.onInputChanged(fakeName, fakeEmail)
        // ASSERT
        val expectedState = AddCustomerActivityState.InvalidInput(true, true, false)
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests that when adding a customer succeeds,
     * the state is updated to [AddCustomerActivityState.AddCustomerSuccess].
     */
    @Test
    fun addCustomer_ReturnsAddCustomerSuccess() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = "alice@mail.com"
        val fakeResponse = CustomerResult.AddCustomerSuccess
        // Mocking dependencies
        `when`(mockCustomerRepository.addCustomer(fakeName, fakeEmail)).thenReturn(flow {
            emit(
                fakeResponse
            )
        })
        // ACT
        viewModel.addCustomer(fakeName, fakeEmail)
        // Advances the coroutine to allow the ViewModel to emit the new value
        advanceUntilIdle()
        // ASSERT
        val expectedState = AddCustomerActivityState.AddCustomerSuccess
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests that when adding a customer fails,
     * the state is updated to [AddCustomerActivityState.AddCustomerError]
     * with the corresponding error message.
     */
    @Test
    fun addCustomer_ReturnsAddCustomerError() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = "alice@mail.com"
        val fakeExceptionMessage = "Failed to add customer"
        val fakeResponse = CustomerResult.AddCustomerError(fakeExceptionMessage)
        // Mocking dependencies
        `when`(mockCustomerRepository.addCustomer(fakeName, fakeEmail)).thenReturn(flow {
            emit(
                fakeResponse
            )
        })
        // ACT
        viewModel.addCustomer(fakeName, fakeEmail)
        // Advances the coroutine to allow the ViewModel to emit the new value
        advanceUntilIdle()
        // ASSERT
        val expectedState = AddCustomerActivityState.AddCustomerError(fakeExceptionMessage)
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState, collectedState)
    }

}