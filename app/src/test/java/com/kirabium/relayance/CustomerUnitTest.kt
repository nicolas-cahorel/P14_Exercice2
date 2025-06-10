package com.kirabium.relayance

import com.kirabium.relayance.domain.model.Customer
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar

/**
 * Unit tests for the [Customer] class.
 *
 * These tests verify the behavior of the [Customer.isNewCustomer] function
 * for different creation dates to ensure that the customer is correctly
 * identified as new or not based on the creation date relative to the current date.
 */
class CustomerUnitTest {

    /**
     * Tests that [Customer.isNewCustomer] returns true for a customer
     * created on the current day.
     */
    @Test
    fun test_IsNewCustomer_ReturnsTrue_ForTodayCustomer() {
        // ARRANGE
        val fakeCreationDate = Calendar.getInstance().time // Today
        val fakeCustomer = Customer(2, "Jim Nastyk", "jim.nastyk@test.com", fakeCreationDate)
        // ACT
        val collectedResult = fakeCustomer.isNewCustomer()
        // ASSERT
        assertTrue(collectedResult)
    }

    /**
     * Tests that [Customer.isNewCustomer] returns true for a customer
     * created less than 3 months ago.
     */
    @Test
    fun test_IsNewCustomer_ReturnsTrue_ForLessThan3MonthsCustomer() {
        // ARRANGE
        val fakeCreationDate = Calendar.getInstance().apply {
            add(Calendar.MONTH, -2)
        }.time // 2 months ago
        val fakeCustomer = Customer(2, "Jim Nastyk", "jim.nastyk@test.com", fakeCreationDate)
        // ACT
        val collectedResult = fakeCustomer.isNewCustomer()
        // ASSERT
        assertTrue(collectedResult)
    }

    /**
     * Tests that [Customer.isNewCustomer] returns false for a customer
     * created more than 3 months ago.
     */
    @Test
    fun test_IsNewCustomer_ReturnsFalse_ForMoreThan3MonthsCustomer() {
        // ARRANGE
        val fakeCreationDate = Calendar.getInstance().apply {
            add(Calendar.MONTH, -4)
        }.time // 4 months ago
        val fakeCustomer = Customer(2, "Jim Nastyk", "jim.nastyk@test.com", fakeCreationDate)
        // ACT
        val collectedResult = fakeCustomer.isNewCustomer()
        // ASSERT
        assertFalse(collectedResult)
    }

}