package com.kirabium.relayance.ui.activity.add

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.os.Bundle
import android.util.TypedValue
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kirabium.relayance.R
import com.kirabium.relayance.databinding.ActivityAddCustomerBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * Activity for adding a new customer.
 * Provides input fields for name and email, and displays validation feedback.
 * Uses a ViewModel to manage UI state and perform business logic.
 */
@AndroidEntryPoint
class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding

    private val viewModel: AddCustomerActivityViewModel by viewModels()

    /**
     * Called when the activity is starting.
     * Sets up the toolbar, view binding, listeners, and observers.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupToolbar()
        setupBinding()
        setupListeners()
        observeViewModel()
    }

    /**
     * Configures the toolbar to display the "back" button.
     */
    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    /**
     * Initializes view binding and sets the content view.
     */
    private fun setupBinding() {
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    /**
     * Sets up text change listeners for input fields and a click listener for the save button.
     * These listeners trigger validation and data submission in the ViewModel.
     */
    private fun setupListeners() {
        binding.nameEditText.addTextChangedListener { nameText ->
            viewModel.onInputChanged(nameText.toString(), binding.emailEditText.text.toString())
        }

        binding.emailEditText.addTextChangedListener { emailText ->
            viewModel.onInputChanged(binding.nameEditText.text.toString(), emailText.toString())
        }

        binding.saveFab.setOnClickListener {
            viewModel.addCustomer(
                binding.nameEditText.text.toString(),
                binding.emailEditText.text.toString()
            )
        }
    }

    /**
     * Observes state changes from the ViewModel and updates the UI accordingly.
     * Handles loading, validation, error, and success states.
     */
    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addCustomerActivityState.collect { state ->
                    when (state) {
                        is AddCustomerActivityState.Loading -> {
                            binding.saveFab.isEnabled = false
                            binding.saveFab.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    this@AddCustomerActivity,
                                    R.color.button_disabled
                                )
                            )
                        }

                        is AddCustomerActivityState.ValidInput -> {
                            binding.saveFab.isEnabled = true
                            val typedValue = TypedValue()
                            theme.resolveAttribute(android.R.attr.colorPrimary, typedValue, true)
                            val colorPrimary = typedValue.data
                            binding.saveFab.backgroundTintList =
                                ColorStateList.valueOf(colorPrimary)
                            binding.nameTextInputLayout.error = null
                            binding.emailTextInputLayout.error = null
                        }

                        is AddCustomerActivityState.InvalidInput -> {
                            binding.saveFab.isEnabled = false
                            binding.saveFab.backgroundTintList = ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    this@AddCustomerActivity,
                                    R.color.button_disabled
                                )
                            )
                            if (state.isNameEmpty) {
                                binding.nameTextInputLayout.error = "name required"
                            } else {
                                binding.nameTextInputLayout.error = null
                            }

                            if (state.isEmailEmpty || !state.isEmailFormatCorrect) {
                                binding.emailTextInputLayout.error = "valid email required"
                            } else {
                                binding.emailTextInputLayout.error = null
                            }
                        }

                        is AddCustomerActivityState.AddCustomerSuccess -> {
                            val intent = Intent()
                            intent.putExtra("RESULT_MESSAGE", "Customer added")
                            setResult(Activity.RESULT_OK, intent)
                            finish()
                        }

                        is AddCustomerActivityState.AddCustomerError -> {
                            Toast.makeText(
                                this@AddCustomerActivity,
                                state.errorMessage,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
            }
        }
    }

}