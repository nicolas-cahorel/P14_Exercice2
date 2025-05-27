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

@ExperimentalCoroutinesApi
class CustomerRepositoryUnitTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockCustomerApi: CustomerApi

    @Mock
    private lateinit var repository: CustomerRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        repository = CustomerRepository(mockCustomerApi)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

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
        assertEquals(expectedResult,collectedResult)
    }

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
        assertEquals(expectedResult,collectedResult)
    }

    @Test
    fun getCustomers_ReturnsError() = runTest {
        // ARRANGE
        val fakeResponse = "Exception Message"
        // Mocking dependencies
        `when`(mockCustomerApi.getCustomers()).thenReturn(
            kotlinx.coroutines.flow.flow { throw RuntimeException(fakeResponse) }
        )
        // ACT
        val collectedResult = repository.getCustomers().first()
        // ASSERT
        val expectedResult = CustomerResult.GetCustomersError(fakeResponse)
        assertEquals(expectedResult,collectedResult)
    }

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
        assertEquals(expectedResult,collectedResult)
    }

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
        assertEquals(expectedResult,collectedResult)
    }

    @Test
    fun addCustomer_ReturnsErrorException() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = "alice@mail.com"
        val fakeResponse = "Exception Message"
        // Mocking dependencies
        `when`(mockCustomerApi.addCustomer(fakeName, fakeEmail)).thenReturn(
            kotlinx.coroutines.flow.flow { throw RuntimeException(fakeResponse) }
        )
        // ACT
        val collectedResult = repository.addCustomer(fakeName, fakeEmail).first()
        // ASSERT
        val expectedResult = CustomerResult.AddCustomerError(fakeResponse)
        assertEquals(expectedResult,collectedResult)
    }

}