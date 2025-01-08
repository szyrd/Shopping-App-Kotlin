package com.example.shoppingapp.data

data class Payment(
    val paymentId: Int,
    val orderId: Int,
    val amount: Double,
    val paymentDate: String,
    val paymentMethod: String
)