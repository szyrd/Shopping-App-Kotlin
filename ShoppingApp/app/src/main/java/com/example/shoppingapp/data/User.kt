package com.example.shoppingapp.data
import kotlinx.serialization.Serializable

@Serializable
data class User(
    val userId: Int,
    val username: String,
    val email: String,
    val passwordHash: String
)