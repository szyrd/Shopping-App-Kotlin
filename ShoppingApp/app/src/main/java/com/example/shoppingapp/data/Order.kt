package com.example.shoppingapp.data
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class Order(
    @PrimaryKey(autoGenerate = true) val orderId: Int = 0,
    val userId: Int,
    val orderDate: String,
    val totalAmount: Double,
    val status: String
)