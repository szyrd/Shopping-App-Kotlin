package com.example.shoppingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.data.OrderItem

class OrderItemAdapter(private val orderItems: List<OrderItem>) :
    RecyclerView.Adapter<OrderItemAdapter.OrderItemViewHolder>() {

    class OrderItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val productId: TextView = view.findViewById(R.id.productIdTextView)
        val quantity: TextView = view.findViewById(R.id.quantityTextView)
        val price: TextView = view.findViewById(R.id.priceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_order_item, parent, false) // Use new layout
        return OrderItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderItemViewHolder, position: Int) {
        val item = orderItems[position]
        holder.productId.text = "Product ID: ${item.productId}"
        holder.quantity.text = "Quantity: ${item.quantity}"
        holder.price.text = "Price: $${item.price}"
    }

    override fun getItemCount(): Int = orderItems.size
}
