package com.kirabium.relayance.ui.activity.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kirabium.relayance.databinding.ActivityMainBinding
import com.kirabium.relayance.ui.activity.add.AddCustomerActivity
import com.kirabium.relayance.ui.activity.detail.DetailActivity
import com.kirabium.relayance.ui.adapter.CustomerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var customerAdapter: CustomerAdapter

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupFab()
        setupCustomerRecyclerView()
    }

    /**
     * Binds the layout using ViewBinding.
     */
    private fun setupBinding() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    /**
     * Configures the FAB to open the AddCustomerActivity.
     */
    private fun setupFab() {
        binding.addCustomerFab.setOnClickListener {
            val intent = Intent(this, AddCustomerActivity::class.java)
            startActivity(intent)
        }
    }

    /**
     * Initializes the RecyclerView and its adapter.
     */
    private fun setupCustomerRecyclerView() {
        binding.customerRecyclerView.layoutManager = LinearLayoutManager(this)

        lifecycleScope.launch {
            viewModel.mainActivityState.collect { state ->
                when (state) {
                    is MainActivityState.Loading -> {
                         binding.progressBar.visibility = View.VISIBLE
                    }

                    is MainActivityState.DisplayCustomers -> {
                         binding.progressBar.visibility = View.GONE

                        customerAdapter = CustomerAdapter(state.customers) { customer ->
                            val intent = Intent(this@MainActivity, DetailActivity::class.java).apply {
                                putExtra(DetailActivity.EXTRA_CUSTOMER_ID, customer.id)
                            }
                            startActivity(intent)
                        }
                        binding.customerRecyclerView.adapter = customerAdapter
                    }

                    is MainActivityState.NoCustomerToDisplay -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, state.stateMessage, Toast.LENGTH_SHORT).show()
                    }

                    is MainActivityState.DisplayErrorMessage -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this@MainActivity, state.stateMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}


