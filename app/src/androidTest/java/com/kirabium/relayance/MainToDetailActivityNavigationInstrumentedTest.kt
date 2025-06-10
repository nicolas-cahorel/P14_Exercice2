package com.kirabium.relayance

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.kirabium.relayance.ui.activity.detail.DetailActivity
import com.kirabium.relayance.ui.activity.main.MainActivity
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test to verify navigation from [MainActivity] to [DetailActivity].
 * It checks that clicking on the first candidate in the list launches the detail screen
 * with the correct customer ID passed as an Intent extra.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class MainToDetailActivityNavigationInstrumentedTest {

    /**
     * Initializes Espresso Intents before each test to enable verification of launched Intents.
     */
    @Before
    fun setUp() {
        Intents.init()
    }

    /**
     * Releases Espresso Intents after each test to clean up resources.
     */
    @After
    fun tearDown() {
        Intents.release()
    }

    /**
     * Launches [MainActivity], performs a click on the first item in the RecyclerView,
     * and asserts that [DetailActivity] is launched with the correct Intent extra.
     *
     * This ensures that clicking on a customer correctly triggers navigation and data passing.
     */
    @Test
    fun test_WhenClickingOnFirstCandidate_thenDetailActivityIsLaunchedWithCorrectId() {
        // Launch the activity containing the candidate list
        val scenario = ActivityScenario.launch(MainActivity::class.java)

        // Wait for RecyclerView to be displayed
        onView(withId(R.id.customerRecyclerView))
            .check(matches(isDisplayed()))

        // Simulate click on the first item of the RecyclerView
        onView(withId(R.id.customerRecyclerView))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
            )

        // Check that an intent with the correct component and extra was sent
        Intents.intended(
            allOf(
                hasComponent(DetailActivity::class.java.name),
                hasExtra("customer_id", 1)
            )
        )

        // Close scenario
        scenario.close()
    }

}