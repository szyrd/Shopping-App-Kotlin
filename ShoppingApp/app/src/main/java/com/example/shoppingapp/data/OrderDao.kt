package com.example.shoppingapp.data

import androidx.room.*

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: Order)

    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<Order>
}