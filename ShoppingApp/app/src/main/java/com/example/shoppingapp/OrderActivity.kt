package com.example.shoppingapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.adapters.OrderItemAdapter
import com.example.shoppingapp.data.*
import android.app.DatePickerDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class OrderActivity : AppCompatActivity() {

    private lateinit var orderRecyclerView: RecyclerView
    private lateinit var orderItemAdapter: OrderItemAdapter

    private lateinit var totalAmountTextView: TextView
    private lateinit var paymentAmountTextView: TextView
    private lateinit var payNowButton: Button

    private lateinit var streetEditText: EditText
    private lateinit var cityEditText: EditText
    private lateinit var stateEditText: EditText
    private lateinit var zipCodeEditText: EditText
    private lateinit var paymentDateEditText: EditText
    private lateinit var paymentMethodEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Initialize Views
        orderRecyclerView = findViewById(R.id.orderRecyclerView)
        totalAmountTextView = findViewById(R.id.totalAmountTextView)
        paymentAmountTextView = findViewById(R.id.paymentAmountTextView)
        payNowButton = findViewById(R.id.payNowButton)

        streetEditText = findViewById(R.id.streetEditText)
        cityEditText = findViewById(R.id.cityEditText)
        stateEditText = findViewById(R.id.stateEditText)
        zipCodeEditText = findViewById(R.id.zipCodeEditText)
        paymentDateEditText = findViewById(R.id.paymentDateEditText)
        paymentMethodEditText = findViewById(R.id.paymentMethodEditText)

        val cartItems = intent.getParcelableArrayListExtra<CartItem>("cartItems") ?: emptyList()

        // Load product prices dynamically
        val database = AppDatabase.getDatabase(this)
        val productDao = database.productDao()

        CoroutineScope(Dispatchers.IO).launch {
            val productList = productDao.getAllProducts()
            val orderItems = cartItems.mapIndexed { index, cartItem ->
                val productPrice = productList.find { it.productId == cartItem.productId }?.price ?: 0.0
                OrderItem(
                    orderItemId = index + 1,
                    orderId = 100, // Example order ID
                    productId = cartItem.productId,
                    quantity = cartItem.quantity,
                    price = productPrice
                )
            }

            withContext(Dispatchers.Main) {
                setupOrderItemsRecyclerView(orderItems)
                val totalAmount = orderItems.sumOf { it.quantity * it.price }
                totalAmountTextView.text = "Total Amount: $%.2f".format(totalAmount)
                paymentAmountTextView.text = "Amount: $%.2f".format(totalAmount)
            }
        }

        paymentDateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate = String.format("%02d/%02d/%04d", selectedDay, selectedMonth + 1, selectedYear)
                paymentDateEditText.setText(formattedDate)
            }, year, month, day)

            datePickerDialog.show()
        }
    }

    private fun setupOrderItemsRecyclerView(orderItems: List<OrderItem>) {
        orderRecyclerView.layoutManager = LinearLayoutManager(this)
        orderItemAdapter = OrderItemAdapter(orderItems)
        orderRecyclerView.adapter = orderItemAdapter
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



