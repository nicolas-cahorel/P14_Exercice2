package com.kirabium.relayance.ui.activity.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.kirabium.relayance.ui.activity.detail.DetailActivity.Companion.EXTRA_CUSTOMER_ID
import com.kirabium.relayance.ui.composable.DetailScreen
import dagger.hilt.android.AndroidEntryPoint

/**
 * Activity that displays the detailed information of a customer using Jetpack Compose.
 *
 * The activity observes the UI state from [DetailActivityViewModel] and
 * renders the [DetailScreen] composable accordingly.
 *
 * It expects an extra with key [EXTRA_CUSTOMER_ID] in the launching Intent to identify
 * which customer's details to display.
 */
@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        /**
         * Key for the Intent extra that holds the ID of the customer to be displayed.
         */
        const val EXTRA_CUSTOMER_ID = "customer_id"
    }

    private val detailActivityViewModel: DetailActivityViewModel by viewModels()

    /**
     * Called when the activity is created. Sets the Compose content and observes
     * the state from [detailActivityViewModel] to update the UI accordingly.
     *
     * @param savedInstanceState The saved state of the activity, if any.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val detailActivityState by detailActivityViewModel.detailActivityState.collectAsState()
            DetailScreen(
                detailActivityState = detailActivityState,
                onBackClicked = { onBackPressedDispatcher.onBackPressed() }
            )
        }

    }

}