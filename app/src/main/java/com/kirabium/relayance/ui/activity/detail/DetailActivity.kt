package com.kirabium.relayance.ui.activity.detail

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.kirabium.relayance.ui.composable.DetailScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CUSTOMER_ID = "customer_id"
    }

    private val detailActivityViewModel: DetailActivityViewModel by viewModels()

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