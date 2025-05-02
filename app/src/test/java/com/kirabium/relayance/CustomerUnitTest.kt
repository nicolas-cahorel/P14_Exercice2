package com.kirabium.relayance

import com.kirabium.relayance.domain.model.Customer
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.util.Calendar

/**
 * Unit tests for the [Customer] class.
 * These tests verify the behavior of the [Customer.isNewCustomer] function
 * for various customer creation dates, ensuring the correct identification
 * of new customers based on their creation date relative to the current date.
 */
class CustomerUnitTest {

    /**
     * Tests the [Customer.isNewCustomer] function to ensure it returns true
     * for customers created today.
     */
    @Test
    fun test_IsNewCustomer_ReturnsTrue_ForTodayCustomer() {
        // ARRANGE
        val creationDate = Calendar.getInstance().time // Today
        val fakeCustomer = Customer(2, "Jim Nastyk", "jim.nastyk@test.com", creationDate)

        // ACT
        val isCustomerNewResult = fakeCustomer.isNewCustomer()

        // ASSERT
        assertTrue(isCustomerNewResult)
    }

    /**
     * Tests the [Customer.isNewCustomer] function to ensure it returns true
     * for customers created less than 3 months ago.
     */
    @Test
    fun test_IsNewCustomer_ReturnsTrue_ForLessThan3MonthsCustomer() {
        // ARRANGE
        val creationDate = Calendar.getInstance().apply {
            add(Calendar.MONTH, -2)
        }.time // 2 months ago
        val fakeCustomer = Customer(2, "Jim Nastyk", "jim.nastyk@test.com", creationDate)

        // ACT
        val isCustomerNewResult = fakeCustomer.isNewCustomer()

        // ASSERT
        assertTrue(isCustomerNewResult)
    }

    /**
     * Tests the [Customer.isNewCustomer] function to ensure it returns false
     * for customers created more than 3 months ago.
     */
    @Test
    fun test_IsNewCustomer_ReturnsFalse_ForMoreThan3MonthsCustomer() {
        // ARRANGE
        val creationDate = Calendar.getInstance().apply {
            add(Calendar.MONTH, -4)
        }.time // 4 months ago
        val fakeCustomer = Customer(2, "Jim Nastyk", "jim.nastyk@test.com", creationDate)

        // ACT
        val isCustomerNewResult = fakeCustomer.isNewCustomer()

        // ASSERT
        assertFalse(isCustomerNewResult)
    }

}