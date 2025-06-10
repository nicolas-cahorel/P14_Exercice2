package com.kirabium.relayance.ui.composable

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kirabium.relayance.R
import com.kirabium.relayance.domain.model.Customer
import com.kirabium.relayance.extension.DateExt.Companion.toHumanDate
import com.kirabium.relayance.ui.activity.detail.DetailActivityState
import java.util.Date

/**
 * Composable that renders the detail screen showing customer information or relevant UI states.
 *
 * This composable handles the following states from [DetailActivityState]:
 * - Loading: Displays a centered progress indicator.
 * - DisplayCustomer: Shows customer details inside a card, with a top app bar including a back button.
 * - DisplayErrorMessage: Shows a Toast message with the error.
 *
 * @param detailActivityState The current state of the detail screen.
 * @param onBackClicked Lambda invoked when the back button is clicked.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    detailActivityState: DetailActivityState,
    onBackClicked: () -> Unit,
) {
    val context = LocalContext.current


    when (detailActivityState) {

        is DetailActivityState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is DetailActivityState.DisplayCustomer -> {
            val customer = detailActivityState.customer
            Scaffold(
                modifier = Modifier,
                topBar = {

                    TopAppBar(
                        title = {
                            Text(stringResource(id = R.string.detail_screen_title))
                        },
                        navigationIcon = {
                            IconButton(onClick = {
                                onBackClicked()
                            }) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(id = R.string.contentDescription_go_back)
                                )
                            }
                        }
                    )
                }
            ) { contentPadding ->
                Column(
                    modifier = Modifier
                        .padding(contentPadding)
                        .fillMaxSize()
                        .background(Color(0xFFF3F3F3))
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(8.dp)
                    ) {
                        Box(modifier = Modifier.padding(24.dp)) {
                            Column(
                                horizontalAlignment = Alignment.Start,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = customer.name,
                                    modifier = Modifier.testTag("CustomerName"),
                                    style = MaterialTheme.typography.headlineMedium.copy(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = customer.email,
                                    modifier = Modifier.testTag("CustomerEmail"),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 16.sp
                                    )
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(
                                    text = stringResource(
                                        id = R.string.created_at,
                                        customer.createdAt.toHumanDate()
                                    ),
                                    modifier = Modifier.testTag("CustomerDate"),
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        fontSize = 16.sp
                                    )
                                )
                            }
                            if (customer.isNewCustomer())
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .offset(x = 24.dp, y = (-24).dp)
                                        .rotate(45f)
                                        .background(Color.Red)
                                        .padding(8.dp)
                                ) {
                                    Text(
                                        text = stringResource(id = R.string.new_ribbon),
                                        modifier = Modifier.testTag("NewRibbon"),
                                        color = Color.White,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                }
                        }
                    }
                }
            }
        }

        is DetailActivityState.DisplayErrorMessage -> {
            LaunchedEffect(detailActivityState) {
                Toast.makeText(
                    context,
                    detailActivityState.stateMessage,
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }
}

/**
 * Preview composable for [DetailScreen] to visualize UI during development.
 */
@Preview
@Composable
private fun DetailScreenPreview() {
    DetailScreen(
        detailActivityState = DetailActivityState.DisplayCustomer(
            customer = Customer(
                id = 0,
                name = "Nom du Client",
                email = "email@client.com",
                createdAt = Date()
            )
        ),
        onBackClicked = {}
    )
}