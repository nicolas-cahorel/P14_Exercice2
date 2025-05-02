package com.kirabium.relayance

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.kirabium.relayance.ui.activity.main.MainActivity
import com.kirabium.relayance.util.RecyclerViewItemCountAssertion
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test to validate that the RecyclerView in [MainActivity]
 * displays exactly 5 candidates when the application is started.
 */
@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentedTest {

    /**
     * Launches [MainActivity] before each test.
     */
    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    /**
     * Asserts that the candidate list in [MainActivity] contains exactly 5 items
     * when the application starts.
     *
     * This test ensures that the initial dummy data or repository setup correctly loads
     * and binds 5 candidates into the RecyclerView.
     */
    @Test
    fun test_CandidateList_ShouldContainFiveItems_OnAppStart() {
        // Checks that the RecyclerView displays exactly 5 items
        onView(withId(R.id.customerRecyclerView))
            .check(RecyclerViewItemCountAssertion.withItemCount(5))
    }

}