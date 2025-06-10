package com.kirabium.relayance.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kirabium.relayance.databinding.CustomerItemBinding
import com.kirabium.relayance.domain.model.Customer

/**
 * RecyclerView Adapter for displaying a list of [Customer] items.
 *
 * @property customers The list of customers to display.
 * @property onClick Lambda function invoked when a customer item is clicked.
 */
class CustomerAdapter(
    private var customers: List<Customer>,
    private val onClick: (Customer) -> Unit
) : RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder>() {

    /**
     * ViewHolder representing a single customer item view.
     *
     * @property binding ViewBinding for the customer item layout.
     * @property onClick Click listener for the customer item.
     */
    class CustomerViewHolder(
        private val binding: CustomerItemBinding,
        val onClick: (Customer) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        private var currentCustomer: Customer? = null

        init {
            binding.root.setOnClickListener {
                currentCustomer?.let {
                    onClick(it)
                }
            }
        }

        /**
         * Binds the given [customer] data to the item view.
         *
         * @param customer The customer data to bind.
         */
        fun bind(customer: Customer) {
            currentCustomer = customer
            with(binding) {
                nameTextView.text = customer.name
                emailTextView.text = customer.email
            }
        }
    }

    /**
     * Creates and returns a new [CustomerViewHolder] instance.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding =
            CustomerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding, onClick)
    }

    /**
     * Binds the [Customer] data at the given [position] to the provided [holder].
     */
    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer = customers[position]
        holder.bind(customer)
    }

    /**
     * Returns the total number of customers to be displayed.
     */
    override fun getItemCount() = customers.size

}
