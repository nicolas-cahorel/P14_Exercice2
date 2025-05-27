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

@ExperimentalCoroutinesApi
class AddCustomerActivityViewModelUnitTest {

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockCustomerRepository: CustomerRepository

    private lateinit var viewModel: AddCustomerActivityViewModel


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        MockitoAnnotations.openMocks(this)
        viewModel = AddCustomerActivityViewModel(mockCustomerRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

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
        assertEquals(expectedState,collectedState)
    }

    @Test
    fun onInputChanged_NameEmpty_ReturnsInvalid() = runTest {
        // ARRANGE
        val fakeName = ""
        val fakeEmail = "alice@mail.com"
        // ACT
        viewModel.onInputChanged(fakeName, fakeEmail)
        // ASSERT
        val expectedState = AddCustomerActivityState.InvalidInput(true,false,true)
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState,collectedState)
    }

    @Test
    fun onInputChanged_EmailEmpty_ReturnsInvalid() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = ""
        // ACT
        viewModel.onInputChanged(fakeName, fakeEmail)
        // ASSERT
        val expectedState = AddCustomerActivityState.InvalidInput(false,true,false)
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState,collectedState)
    }

    @Test
    fun onInputChanged_EmailIncorrectFormat_ReturnsInvalid() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = "alice.mail.com"
        // ACT
        viewModel.onInputChanged(fakeName, fakeEmail)
        // ASSERT
        val expectedState = AddCustomerActivityState.InvalidInput(false,false,false)
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState,collectedState)
    }

    @Test
    fun onInputChanged_NameAndEmailEmpty_ReturnsInvalid() = runTest {
        // ARRANGE
        val fakeName = ""
        val fakeEmail = ""
        // ACT
        viewModel.onInputChanged(fakeName, fakeEmail)
        // ASSERT
        val expectedState = AddCustomerActivityState.InvalidInput(true,true,false)
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState,collectedState)
    }

    @Test
    fun addCustomer_ReturnsAddCustomerSuccess() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = "alice@mail.com"
        // Mocking dependencies
        `when`(mockCustomerRepository.addCustomer(fakeName, fakeEmail)).thenReturn(flow {emit(CustomerResult.AddCustomerSuccess)})
        // ACT
        viewModel.addCustomer(fakeName, fakeEmail)
        advanceUntilIdle()
        // ASSERT
        val expectedState = AddCustomerActivityState.AddCustomerSuccess
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState, collectedState)
    }

    @Test
    fun addCustomer_ReturnsAddCustomerError() = runTest {
        // ARRANGE
        val fakeName = "Alice"
        val fakeEmail = "alice@mail.com"
        // Mocking dependencies
        `when`(mockCustomerRepository.addCustomer(fakeName, fakeEmail)).thenReturn(flow {emit(CustomerResult.AddCustomerError("Failed to add customer"))})
        // ACT
        viewModel.addCustomer(fakeName, fakeEmail)
        advanceUntilIdle()
        // ASSERT
        val expectedState = AddCustomerActivityState.AddCustomerError("Failed to add customer")
        val collectedState = viewModel.addCustomerActivityState.first()
        assertEquals(expectedState, collectedState)
    }

}