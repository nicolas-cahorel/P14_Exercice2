package com.kirabium.relayance

import com.kirabium.relayance.data.DummyData
import org.junit.Test
import java.util.Calendar
import org.junit.Assert.*

/**
 * Unit tests for the [DummyData] object.
 * These tests verify the behavior of the [DummyData.generateDate] function
 * for different values of months to go back.
 */
class DummyDataUnitTest {

    /**
     * Tests the [DummyData.generateDate] function to ensure it correctly returns a date
     * that is the specified number of months in the past.
     *
     * This test runs for various values of [monthsBack] (0, 1, 6, 12, 100) and verifies
     * that the generated date matches the expected date after subtracting the corresponding
     * number of months from the current date.
     */
    @Test
    fun test_GenerateDate_ReturnsCorrectPastDate() {
        // ARRANGE
        val testCases = listOf(0,1,6,12,100)

        testCases.forEach { monthsBack ->
            val expectedDate = Calendar.getInstance().apply {
                add(Calendar.MONTH, -monthsBack)
            }

            // ACT
            val resultDate = Calendar.getInstance().apply {
                time = DummyData.generateDate(monthsBack)
            }

            // ASSERT
            assertEquals(expectedDate.get(Calendar.YEAR), resultDate.get(Calendar.YEAR))
            assertEquals(expectedDate.get(Calendar.MONTH), resultDate.get(Calendar.MONTH))

        }
    }

}