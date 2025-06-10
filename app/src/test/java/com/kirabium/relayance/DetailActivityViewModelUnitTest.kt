package com.kirabium.relayance

import androidx.lifecycle.SavedStateHandle
import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.repository.CustomerResult
import com.kirabium.relayance.data.service.CustomerFakeApi.Companion.generateDateMonthsAgo
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.activity.detail.DetailActivity
import com.kirabium.relayance.ui.activity.detail.DetailActivityState
import com.kirabium.relayance.ui.activity.detail.DetailActivityViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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
 * Unit tests for [DetailActivityViewModel].
 *
 * These tests verify the initialization behavior of the ViewModel,
 * checking the emitted [DetailActivityState] for different scenarios when
 * retrieving customer data from the repository.
 */
@ExperimentalCoroutinesApi
class DetailActivityViewModelUnitTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockCustomerRepository: CustomerRepository

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var viewModel: DetailActivityViewModel

    /**
     * Setup test environment before each test,
     * including setting the main dispatcher and initializing mocks.
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
    }

    /**
     * Reset main dispatcher after each test to avoid interference.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Tests that the ViewModel emits [DetailActivityState.DisplayCustomer] with the correct
     * customer when the repository returns a successful list containing the requested customer ID.
     */
    @Test
    fun init_ReturnsDisplayCustomer_GetCustomersSuccess() = runTest {
        // ARRANGE
        val fakeCustomerId = 1
        val fakeCustomer1 =
            Customer(1, "Alice Wonderland", "alice@example.com", generateDateMonthsAgo(12))
        val fakeCustomer2 = Customer(2, "Bob Builder", "bob@example.com", generateDateMonthsAgo(6))
        val fakeResponse = CustomerResult.GetCustomersSuccess(listOf(fakeCustomer1, fakeCustomer2))
        savedStateHandle =
            SavedStateHandle(mapOf(DetailActivity.EXTRA_CUSTOMER_ID to fakeCustomerId))
        // Mocking dependencies
        `when`(mockCustomerRepository.getCustomers()).thenReturn(flowOf(fakeResponse))
        // ACT
        viewModel = DetailActivityViewModel(mockCustomerRepository, savedStateHandle)
        // Advances the coroutine to allow the ViewModel to emit the new value
        advanceUntilIdle()
        // ASSERT
        val expectedState = DetailActivityState.DisplayCustomer(fakeCustomer1)
        val collectedState = viewModel.detailActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests that the ViewModel emits [DetailActivityState.DisplayErrorMessage]
     * with "Customer not found" message when the requested customer ID
     * does not exist in the returned customer list.
     */
    @Test
    fun init_ReturnsDisplayErrorMessage_GetCustomersSuccess_CustomerNotFound() = runTest {
        // ARRANGE
        val fakeCustomerId = 3
        val fakeCustomer1 =
            Customer(1, "Alice Wonderland", "alice@example.com", generateDateMonthsAgo(12))
        val fakeCustomer2 = Customer(2, "Bob Builder", "bob@example.com", generateDateMonthsAgo(6))
        val fakeResponse = CustomerResult.GetCustomersSuccess(listOf(fakeCustomer1, fakeCustomer2))
        savedStateHandle =
            SavedStateHandle(mapOf(DetailActivity.EXTRA_CUSTOMER_ID to fakeCustomerId))
        // Mocking dependencies
        `when`(mockCustomerRepository.getCustomers()).thenReturn(flowOf(fakeResponse))
        // ACT
        viewModel = DetailActivityViewModel(mockCustomerRepository, savedStateHandle)
        // Advances the coroutine to allow the ViewModel to emit the new value
        advanceUntilIdle()
        // ASSERT
        val expectedState = DetailActivityState.DisplayErrorMessage("Customer not found")
        val collectedState = viewModel.detailActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests that the ViewModel emits [DetailActivityState.DisplayErrorMessage]
     * with "No customers available" message when the repository returns
     * an empty customer list.
     */
    @Test
    fun init_ReturnsDisplayErrorMessage_getCustomersEmpty() = runTest {
        // ARRANGE
        val fakeCustomerId = 1
        val fakeResponse = CustomerResult.GetCustomersEmpty
        savedStateHandle =
            SavedStateHandle(mapOf(DetailActivity.EXTRA_CUSTOMER_ID to fakeCustomerId))
        // Mocking dependencies
        `when`(mockCustomerRepository.getCustomers()).thenReturn(flowOf(fakeResponse))
        // ACT
        viewModel = DetailActivityViewModel(mockCustomerRepository, savedStateHandle)
        // Advances the coroutine to allow the ViewModel to emit the new value
        advanceUntilIdle()
        // ASSERT
        val expectedState = DetailActivityState.DisplayErrorMessage("No customers available")
        val collectedState = viewModel.detailActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests that the ViewModel emits [DetailActivityState.DisplayErrorMessage]
     * with the exception message when the repository returns an error.
     */
    @Test
    fun init_ReturnsDisplayErrorMessage_getCustomersError() = runTest {
        // ARRANGE
        val fakeCustomerId = 1
        val fakeExceptionMessage = "Exception Message"
        val fakeResponse = CustomerResult.GetCustomersError(fakeExceptionMessage)
        savedStateHandle =
            SavedStateHandle(mapOf(DetailActivity.EXTRA_CUSTOMER_ID to fakeCustomerId))
        // Mocking dependencies
        `when`(mockCustomerRepository.getCustomers()).thenReturn(flowOf(fakeResponse))
        // ACT
        viewModel = DetailActivityViewModel(mockCustomerRepository, savedStateHandle)
        // Advances the coroutine to allow the ViewModel to emit the new value
        advanceUntilIdle()
        // ASSERT
        val expectedState = DetailActivityState.DisplayErrorMessage(fakeExceptionMessage)
        val collectedState = viewModel.detailActivityState.first()
        assertEquals(expectedState, collectedState)
    }

}