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

@ExperimentalCoroutinesApi
class MainActivityViewModelUnitTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockCustomerRepository: CustomerRepository

    private lateinit var viewModel: MainActivityViewModel


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = MainActivityViewModel(mockCustomerRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun fetchData_ReturnsDisplayCustomers() = runTest {
        // ARRANGE
        val fakeCustomers = listOf(
            Customer(1, "Alice Wonderland", "alice@example.com", generateDateMonthsAgo(12)),
            Customer(2, "Bob Builder", "bob@example.com", generateDateMonthsAgo(6))
        )
        // Mocking dependencies
        `when`(mockCustomerRepository.getCustomers()).thenReturn(flow {emit(
            CustomerResult.GetCustomersSuccess(fakeCustomers))})
        // ACT
        viewModel.fetchData()
        // ASSERT
        val expectedState = MainActivityState.DisplayCustomers(fakeCustomers)
        val collectedState = viewModel.mainActivityState.first()
        assertEquals(expectedState,collectedState)
    }

    @Test
    fun fetchData_ReturnsNoCustomerToDisplay() = runTest {
        // ARRANGE
        val fakeCustomers = listOf(
            Customer(1, "Alice Wonderland", "alice@example.com", generateDateMonthsAgo(12)),
            Customer(2, "Bob Builder", "bob@example.com", generateDateMonthsAgo(6))
        )
        // Mocking dependencies
        `when`(mockCustomerRepository.getCustomers()).thenReturn(flow {emit(
            CustomerResult.GetCustomersSuccess(fakeCustomers))})
        // ACT
        viewModel.fetchData()
        // ASSERT
        val expectedState = MainActivityState.DisplayCustomers(fakeCustomers)
        val collectedState = viewModel.mainActivityState.first()
        assertEquals(expectedState,collectedState)
    }

    @Test
    fun fetchData_ReturnsDisplayErrorMessage() = runTest {
        // ARRANGE
        val fakeCustomers = listOf(
            Customer(1, "Alice Wonderland", "alice@example.com", generateDateMonthsAgo(12)),
            Customer(2, "Bob Builder", "bob@example.com", generateDateMonthsAgo(6))
        )
        // Mocking dependencies
        `when`(mockCustomerRepository.getCustomers()).thenReturn(flow {emit(
            CustomerResult.GetCustomersSuccess(fakeCustomers))})
        // ACT
        viewModel.fetchData()
        // ASSERT
        val expectedState = MainActivityState.DisplayCustomers(fakeCustomers)
        val collectedState = viewModel.mainActivityState.first()
        assertEquals(expectedState,collectedState)
    }

}