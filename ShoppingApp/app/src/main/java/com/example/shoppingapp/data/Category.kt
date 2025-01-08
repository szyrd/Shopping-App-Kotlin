package com.example.shoppingapp.data
import kotlinx.serialization.Serializable

@Serializable
data class Category(
    val categoryId: Int,
    val categoryName: String
)