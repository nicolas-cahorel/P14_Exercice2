package com.kirabium.relayance

import com.kirabium.relayance.data.repository.CustomerRepository
import com.kirabium.relayance.data.repository.CustomerResult
import com.kirabium.relayance.data.service.CustomerApi
import com.kirabium.relayance.data.service.CustomerFakeApi.Companion.generateDateMonthsAgo
import com.kirabium.relayance.domain.model.Customer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
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

/**
 * Unit tests for [CustomerRepository].
 *
 * These tests verify the behavior of repository methods when interacting with the
 * underlying [CustomerApi] mock. They cover successful data retrieval, empty data,
 * error handling, and customer addition scenarios.
 */
@ExperimentalCoroutinesApi
class CustomerRepositoryUnitTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockCustomerApi: CustomerApi

    @Mock
    private lateinit var repository: CustomerRepository

    /**
     * Sets up the test environment by initializing mocks and setting the main dispatcher
     * to [testDispatcher].
     */
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        repository = CustomerRepository(mockCustomerApi)
    }

    /**
     * Resets the main dispatcher to the original state after tests.
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    /**
     * Tests that [CustomerRepository.getCustomers] returns a success result
     * with a non-empty list of customers.
     */
    @Test
    fun getCustomers_ReturnsEmpty() = runTest {
        // ARRANGE
        val fakeResponse = listOf(
            Customer(1, "Alice Wonderland", "alice@example.com", generateDateMonthsAgo(12)),
            Customer(2, "Bob Builder", "bob@example.com", generateDateMonthsAgo(6))
        )
        // Mocking dependencies
        `when`(mockCustomerApi.getCustomers()).thenReturn(flowOf(fakeResponse))
        // ACT
        val collectedResult = repository.getCustomers().first()
        // ASSERT
        val expectedResult = CustomerResult.GetCustomersSuccess(fakeResponse)
        assertEquals(expectedResult, collectedResult)
    }

    /**
     * Tests that [CustomerRepository.getCustomers] returns an empty result
     * when the customer list is empty.
     */
    @Test
    fun getCustomers_ReturnsSuccess() = runTest {
        // ARRANGE
        val fakeResponse = emptyList<Customer>()
        // Mocking dependencies
        `when`(mockCustomerApi.getCustomers()).thenReturn(flowOf(fakeResponse))
        // ACT
        val collectedResult = repository.getCustomers().first()
        // ASSERT
        val expectedResult = CustomerResult.GetCustomersEmpty
        assertEquals(expectedResult, collectedResult)
    }

    /**
     * Tests that [CustomerRepository.getCustomers] returns an error result
     * when the underlying API throws an exception.
     */
    @Test
    fun getCustomers_ReturnsError() = runTest {
        // ARRANGE
        val fakeExceptionMessage = "Exception Message"
        // Mocking dependencies
        `when`(mockCustomerApi.getCustomers()).thenReturn(
            kotlinx.coroutines.flow.flow { throw RuntimeException(fakeExceptionMessage) }
        )
        // ACT
        val collectedResult = repository.getCustomers().first()
        // ASSERT
        val expectedResult = CustomerResult.GetCustomersError(fakeExceptionMessage)
        assertEquals(expectedResult, collectedResult)
    }

    /**
     * Tests that [CustomerRepository.addCustomer] returns a success result
     * when the API successfully adds a customer.
     */
    @Test
    fun addCustomer_ReturnsSuccess() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = "alice@mail.com"
        val fakeResponse = true
        // Mocking dependencies
        `when`(mockCustomerApi.addCustomer(fakeName, fakeEmail)).thenReturn(flowOf(fakeResponse))
        // ACT
        val collectedResult = repository.addCustomer(fakeName, fakeEmail).first()
        // ASSERT
        val expectedResult = CustomerResult.AddCustomerSuccess
        assertEquals(expectedResult, collectedResult)
    }

    /**
     * Tests that [CustomerRepository.addCustomer] returns an error result
     * when the API fails to add a customer.
     */
    @Test
    fun addCustomer_ReturnsError() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = "alice@mail.com"
        val fakeResponse = false
        // Mocking dependencies
        `when`(mockCustomerApi.addCustomer(fakeName, fakeEmail)).thenReturn(flowOf(fakeResponse))
        // ACT
        val collectedResult = repository.addCustomer(fakeName, fakeEmail).first()
        // ASSERT
        val expectedResult = CustomerResult.AddCustomerError("Failed to add customer")
        assertEquals(expectedResult, collectedResult)
    }

    /**
     * Tests that [CustomerRepository.addCustomer] returns an error result
     * when the API throws an exception during the addition.
     */
    @Test
    fun addCustomer_ReturnsErrorException() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = "alice@mail.com"
        val fakeExceptionMessage = "Exception Message"
        // Mocking dependencies
        `when`(mockCustomerApi.addCustomer(fakeName, fakeEmail)).thenReturn(
            kotlinx.coroutines.flow.flow { throw RuntimeException(fakeExceptionMessage) }
        )
        // ACT
        val collectedResult = repository.addCustomer(fakeName, fakeEmail).first()
        // ASSERT
        val expectedResult = CustomerResult.AddCustomerError(fakeExceptionMessage)
        assertEquals(expectedResult, collectedResult)
    }

}