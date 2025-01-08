package com.example.shoppingapp.ui.theme

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.OrderActivity
import com.example.shoppingapp.R
import com.example.shoppingapp.adapters.CartAdapter
import com.example.shoppingapp.data.AppDatabase
import com.example.shoppingapp.data.CartItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.shoppingapp.data.Product
import com.example.shoppingapp.data.ProductRepository
import com.example.shoppingapp.ui.OrderListActivity

class CartActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter
    private val cartItems = mutableListOf<CartItem>()
    private lateinit var productList: List<Product>
    private lateinit var totalTextView: TextView // Total amount TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        // Setup action bar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = "Shopping Cart"
        }

        // Initialize views
        recyclerView = findViewById(R.id.cartRecyclerView)
        totalTextView = findViewById(R.id.cartTotal) // Ensure TextView is added in XML
        val clearCartButton = findViewById<Button>(R.id.btnClearCart)

        recyclerView.layoutManager = LinearLayoutManager(this)

        // Load products
        productList = ProductRepository.loadProductsFromJson(this)

        // Setup cart adapter
        cartAdapter = CartAdapter(
            cartItems,
            productList,
            { updatedCartItem -> updateCartItem(updatedCartItem) },
            { removedCartItem -> removeCartItem(removedCartItem) }
        )
        recyclerView.adapter = cartAdapter

        // Load cart items
        loadCartItems()

        clearCartButton.setOnClickListener {
            clearCart()
        }
        val placeOrdersButton = findViewById<Button>(R.id.placeOrderButton)

        placeOrdersButton.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            intent.putExtra("cartItems", ArrayList(cartItems)) // Pass cart items as a Parcelable ArrayList
            startActivity(intent)

        }

        // Initialize the viewOrdersButton
        val viewOrdersButton = findViewById<Button>(R.id.viewOrdersButton)

        // Set the click listener
        viewOrdersButton.setOnClickListener {
            // Navigate to OrderListActivity
            val intent = Intent(this, OrderListActivity::class.java)
            startActivity(intent)
        }



    }

    private fun loadCartItems() {
        val database = AppDatabase.getDatabase(this)
        val cartItemDao = database.cartItemDao()

        CoroutineScope(Dispatchers.IO).launch {
            val items = cartItemDao.getAllCartItems()
            withContext(Dispatchers.Main) {
                cartItems.clear()
                cartItems.addAll(items)
                cartAdapter.notifyDataSetChanged()
                calculateTotal() // Update total after loading items
            }
        }
    }

    private fun calculateTotal() {
        var totalAmount = 0.0
        for (cartItem in cartItems) {
            val product = productList.find { it.productId == cartItem.productId }
            if (product != null) {
                totalAmount += product.price * cartItem.quantity
            }
        }
        totalTextView.text = "Total: $%.2f".format(totalAmount) // Update TextView with total
    }

    private fun updateCartItem(updatedCartItem: CartItem) {
        val database = AppDatabase.getDatabase(this)
        val cartItemDao = database.cartItemDao()

        CoroutineScope(Dispatchers.IO).launch {
            cartItemDao.updateCartItem(updatedCartItem)
            loadCartItems()
        }
    }

    private fun removeCartItem(cartItem: CartItem) {
        val database = AppDatabase.getDatabase(this)
        val cartItemDao = database.cartItemDao()

        CoroutineScope(Dispatchers.IO).launch {
            cartItemDao.deleteCartItem(cartItem)
            loadCartItems()
        }
    }

    private fun clearCart() {
        val database = AppDatabase.getDatabase(this)
        val cartItemDao = database.cartItemDao()

        CoroutineScope(Dispatchers.IO).launch {
            cartItemDao.clearCart()
            withContext(Dispatchers.Main) {
                cartItems.clear()
                cartAdapter.notifyDataSetChanged()
                calculateTotal() // Reset total to 0 after clearing
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { // Handle back button in the ActionBar
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}










