package com.example.shoppingapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.R
import com.example.shoppingapp.data.CartItem
import com.example.shoppingapp.data.Product // Import Product model

class CartAdapter(
    private val cartItems: MutableList<CartItem>, // Changed to MutableList for modifications
    private val products: List<Product>,
    private val onQuantityChange: (CartItem) -> Unit,
    private val onRemoveItem: (CartItem) -> Unit // Callback for removing item
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val productName: TextView = itemView.findViewById(R.id.cartProductName)
        val productId: TextView = itemView.findViewById(R.id.cartProductId)
        val quantity: TextView = itemView.findViewById(R.id.cartQuantity)
        val plusButton: Button = itemView.findViewById(R.id.btnIncreaseQuantity)
        val minusButton: Button = itemView.findViewById(R.id.btnDecreaseQuantity)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        // Find product name by matching the product ID
        val product = products.find { it.productId == cartItem.productId }
        holder.productName.text = product?.name ?: "Unknown Product"
        holder.productId.text = "Product ID: ${cartItem.productId}"
        holder.quantity.text = "Quantity: ${cartItem.quantity}"

        // Increase button logic
        holder.plusButton.setOnClickListener {
            cartItem.quantity += 1
            onQuantityChange(cartItem)
            notifyItemChanged(position)
        }

        // Decrease button logic
        holder.minusButton.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity -= 1
                onQuantityChange(cartItem)
                notifyItemChanged(position)
            } else {
                // Remove item if quantity equals 0
                onRemoveItem(cartItem)
                cartItems.removeAt(position)
                notifyItemRemoved(position)
                notifyItemRangeChanged(position, cartItems.size)
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size
}






