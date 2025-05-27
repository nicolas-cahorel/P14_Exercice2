package com.kirabium.relayance

import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import org.junit.Assert.*
import org.junit.Test
import java.util.Calendar
import java.util.GregorianCalendar

/**
 * Unit tests for the [DateExt] class.
 * These tests verify the correct conversion of a Date object to a human-readable string.
 */
class DateExtUnitTest {

    /**
     * Tests the [DateExt.toHumanDate] function to verify it converts a Date
     * into a correctly formatted string (dd/MM/yyyy).
     */
    @Test
    fun test_ToHumanDate_ReturnsCorrectFormattedDate() {
        // ARRANGE
        val fakeDate = GregorianCalendar(2025, Calendar.APRIL, 11).time
        // ACT
        val convertedDateResult = fakeDate.toHumanDate()
        // ASSERT
        assertEquals("11/04/2025", convertedDateResult)
    }

    /**
     * Tests the [DateExt.toHumanDate] function with the current date to ensure
     * it returns a valid date format.
     */
    @Test
    fun test_ToHumanDate_ReturnsValidFormat() {
        // ARRANGE
        val fakeDate = Calendar.getInstance().time
        // ACT
        val convertedDateResult = fakeDate.toHumanDate()
        // ASSERT
        assertTrue(convertedDateResult.matches(Regex("\\d{2}/\\d{2}/\\d{4}")))
    }

}