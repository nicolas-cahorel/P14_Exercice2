package com.kirabium.relayance.ui.activity.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.kirabium.relayance.databinding.ActivityMainBinding
import com.kirabium.relayance.ui.activity.add.AddCustomerActivity
import com.kirabium.relayance.ui.activity.detail.DetailActivity
import com.kirabium.relayance.ui.adapter.CustomerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * MainActivity displays a list of customers and allows navigation to add or view customer details.
 *
 * It observes the [MainActivityViewModel] state to update the UI accordingly,
 * handles user interactions such as adding a new customer and selecting a customer from the list.
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var customerAdapter: CustomerAdapter

    private lateinit var addCustomerLauncher: ActivityResultLauncher<Intent>

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupFab()
        setupCustomerRecyclerView()
        setupLauncher()
    }

    /**
     * Initializes view binding and sets the content view.
     */
    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    /**
     * Configures the Floating Action Button (FAB) to launch [AddCustomerActivity]
     * when clicked to add a new customer.
     */
    private fun setupFab() {
        binding.addCustomerFab.setOnClickListener {
            val intent = Intent(this, AddCustomerActivity::class.java)
            addCustomerLauncher.launch(intent)
        }
    }

    /**
     * Sets up the RecyclerView with a linear layout manager and observes
     * the ViewModel's state to display customers or appropriate messages.
     *
     * The adapter handles item clicks to open the [DetailActivity] for the selected customer.
     */
    private fun setupCustomerRecyclerView() {
        binding.customerRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.mainActivityState.collect { state ->
                    when (state) {
                        is MainActivityState.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }

                        is MainActivityState.DisplayCustomers -> {
                            binding.progressBar.visibility = View.GONE

                            customerAdapter = CustomerAdapter(state.customers) { customer ->
                                val intent =
                                    Intent(this@MainActivity, DetailActivity::class.java).apply {
                                        putExtra(DetailActivity.EXTRA_CUSTOMER_ID, customer.id)
                                    }
                                startActivity(intent)
                            }
                            binding.customerRecyclerView.adapter = customerAdapter
                        }

                        is MainActivityState.NoCustomerToDisplay -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@MainActivity,
                                state.stateMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }

                        is MainActivityState.DisplayErrorMessage -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(
                                this@MainActivity,
                                state.stateMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

    /**
     * Registers an [ActivityResultLauncher] to handle the result of launching
     * the [AddCustomerActivity]. On success, refreshes the customer list and
     * displays any returned message as a Toast.
     */
    private fun setupLauncher() {
        addCustomerLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    viewModel.fetchData()
                    it.data?.getStringExtra("RESULT_MESSAGE")?.let { message ->
                        Toast.makeText(
                            this@MainActivity,
                            message,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
    }

}


