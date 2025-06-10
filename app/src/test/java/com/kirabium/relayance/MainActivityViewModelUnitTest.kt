package com.kirabium.relayance

import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.repository.CustomerResult
import com.kirabium.relayance.data.service.CustomerFakeApi.Companion.generateDateMonthsAgo
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.ui.activity.main.MainActivityState
import com.kirabium.relayance.ui.activity.main.MainActivityViewModel
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
 * Unit tests for [MainActivityViewModel].
 *
 * This class tests different scenarios for the [fetchData] method of the ViewModel,
 * verifying that the UI state is correctly updated based on the repository response.
 */
@ExperimentalCoroutinesApi
class MainActivityViewModelUnitTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockCustomerRepository: CustomerRepository

    private lateinit var viewModel: MainActivityViewModel

    /**
     * Sets up the testing environment before each test.
     * Initializes Mockito mocks and sets the main coroutine dispatcher.
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
    }

    /**
     * Cleans up after each test by resetting the main coroutine dispatcher.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Tests that [MainActivityViewModel.fetchData] correctly updates the state
     * to [MainActivityState.DisplayCustomers] when customers are successfully fetched.
     */
    @Test
    fun fetchData_ReturnsDisplayCustomers() = runTest {
        // ARRANGE
        val fakeCustomers = listOf(
            Customer(1, "Alice Wonderland", "alice@example.com", generateDateMonthsAgo(12)),
            Customer(2, "Bob Builder", "bob@example.com", generateDateMonthsAgo(6))
        )
        val fakeResult = CustomerResult.GetCustomersSuccess(fakeCustomers)
        // Mocking dependencies
        `when`(mockCustomerRepository.getCustomers()).thenReturn(flow { emit(fakeResult) })
        viewModel = MainActivityViewModel(mockCustomerRepository, testDispatcher)
        // ACT
        viewModel.fetchData()
        // Advances the coroutine to allow the ViewModel to emit the new value
        advanceUntilIdle()
        // ASSERT
        val expectedState = MainActivityState.DisplayCustomers(fakeCustomers)
        val collectedState = viewModel.mainActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests that [MainActivityViewModel.fetchData] correctly updates the state
     * to [MainActivityState.NoCustomerToDisplay] when no customers are available.
     */
    @Test
    fun fetchData_ReturnsNoCustomerToDisplay() = runTest {
        // ARRANGE
        val fakeResult = CustomerResult.GetCustomersEmpty
        // Mocking dependencies
        `when`(mockCustomerRepository.getCustomers()).thenReturn(flow { emit(fakeResult) })
        viewModel = MainActivityViewModel(mockCustomerRepository, testDispatcher)
        // ACT
        viewModel.fetchData()
        // Advances the coroutine to allow the ViewModel to emit the new value
        advanceUntilIdle()
        // ASSERT
        val expectedState = MainActivityState.NoCustomerToDisplay("No customer to display")
        val collectedState = viewModel.mainActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    /**
     * Tests that [MainActivityViewModel.fetchData] correctly updates the state
     * to [MainActivityState.DisplayErrorMessage] when an error occurs while fetching customers.
     */
    @Test
    fun fetchData_ReturnsDisplayErrorMessage() = runTest {
        // ARRANGE
        val fakeErrorMessage = "Exception Message"
        val fakeResult = CustomerResult.GetCustomersError(fakeErrorMessage)
        // Mocking dependencies
        `when`(mockCustomerRepository.getCustomers()).thenReturn(flow { emit(fakeResult) })
        viewModel = MainActivityViewModel(mockCustomerRepository, testDispatcher)
        // ACT
        viewModel.fetchData()
        // Advances the coroutine to allow the ViewModel to emit the new value
        advanceUntilIdle()
        // ASSERT
        val expectedState = MainActivityState.DisplayErrorMessage(fakeErrorMessage)
        val collectedState = viewModel.mainActivityState.first()
        assertEquals(expectedState, collectedState)
    }

}