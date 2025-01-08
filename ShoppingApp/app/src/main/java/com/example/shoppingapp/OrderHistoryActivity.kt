package com.example.shoppingapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingapp.adapters.OrderAdapter
import com.example.shoppingapp.data.AppDatabase
import kotlinx.coroutines.*

class OrderHistoryActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_history)

        recyclerView = findViewById(R.id.orderRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val database = AppDatabase.getDatabase(this)
        val orderDao = database.orderDao()

        // Load orders and display them
        CoroutineScope(Dispatchers.IO).launch {
            val orders = orderDao.getAllOrders()
            withContext(Dispatchers.Main) {
                recyclerView.adapter = OrderAdapter(orders)
            }
        }
    }
}

