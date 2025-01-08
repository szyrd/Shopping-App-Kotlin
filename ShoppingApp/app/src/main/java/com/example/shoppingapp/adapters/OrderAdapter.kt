package com.example.shoppingapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.ReviewActivity
import com.example.shoppingapp.data.Order

class OrderAdapter(private val orders: List<Order>) :
    RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    class OrderViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val orderId: TextView = view.findViewById(R.id.orderIdTextView)      // Updated
        val orderDate: TextView = view.findViewById(R.id.orderDateTextView)  // Updated
        val totalAmount: TextView = view.findViewById(R.id.totalAmountTextView) // Updated
        val orderStatus: TextView = view.findViewById(R.id.statusTextView)   // Updated
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false)
        return OrderViewHolder(view)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        holder.orderId.text = "Order ID: ${order.orderId}"
        holder.orderDate.text = "Date: ${order.orderDate}"
        holder.totalAmount.text = "Total: $${order.totalAmount}"
        holder.orderStatus.text = "Status: ${order.status}"

        // Set onClickListener for the Review button
        holder.itemView.findViewById<Button>(R.id.reviewButton).setOnClickListener {
            val intent = Intent(holder.itemView.context, ReviewActivity::class.java)
            intent.putExtra("orderId", order.orderId)
            intent.putExtra("userId", order.userId)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = orders.size
}


