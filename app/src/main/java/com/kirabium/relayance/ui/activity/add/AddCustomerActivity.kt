package com.kirabium.relayance.ui.activity.add

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kirabium.relayance.databinding.ActivityAddCustomerBinding

class AddCustomerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddCustomerBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupBinding()
        setupToolbar()
    }

    private fun setupToolbar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun setupBinding() {
        binding = ActivityAddCustomerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}