package com.kirabium.relayance

import android.content.Intent
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createEmptyComposeRule
import androidx.compose.ui.test.onNodeWithTag
import com.kirabium.relayance.data.DummyData
import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import com.kirabium.relayance.ui.activity.detail.DetailActivity
import com.kirabium.relayance.ui.activity.detail.DetailActivity.Companion.EXTRA_CUSTOMER_ID
import org.junit.Rule
import org.junit.Test

/**
 * Instrumented test class for verifying the UI of [DetailActivity].
 *
 * It ensures that the customer's name, email, creation date, and the "New" ribbon (if applicable)
 * are properly displayed based on the provided dummy data.
 */
class DetailActivityInstrumentedTest {

    /**
     * Provides a Compose testing rule to launch and interact with Compose UI elements.
     */
    @get:Rule
    val composeRule = createEmptyComposeRule()

    /**
     * Launches the [DetailActivity] with a known customer ID and verifies:
     * - The "NewRibbon" is displayed for new customers.
     * - The customer's name, email, and formatted creation date are correctly shown on the screen.
     */
    @Test
    fun test_DetailScreenDisplaysCorrectCustomerInfo() {
        composeRule.launch<DetailActivity>(
            intentFactory = { ctx ->
                Intent(ctx, DetailActivity::class.java).apply {
                    putExtra(EXTRA_CUSTOMER_ID, 1)
                }
            },
            onAfterLaunched = {
                val customer = DummyData.customers.first { it.id == 1 }

                // Check if the "NewRibbon" is displayed for new customers
                if (customer.isNewCustomer()) {
                    onNodeWithTag("NewRibbon").assertIsDisplayed()
                }

                /// Display and content verification for the customer's name
                onNodeWithTag("CustomerName")
                    .assertIsDisplayed()
                    .assertTextEquals(customer.name)

                // Display and content verification for the customer's email
                onNodeWithTag("CustomerEmail")
                    .assertIsDisplayed()
                    .assertTextEquals(customer.email)

                // Display and content verification for the customer's creation date
                onNodeWithTag("CustomerDate")
                    .assertIsDisplayed()
                    .assertTextEquals("Created at: ${customer.createdAt.toHumanDate()}")
            }
        )
    }
}
