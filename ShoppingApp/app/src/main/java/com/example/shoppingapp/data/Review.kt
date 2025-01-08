package com.example.shoppingapp.data

data class Review(
    val reviewId: Int,
    val productId: Int,
    val userId: Int,
    val rating: Float,
    val comment: String
)