package com.example.shoppingapp.data

data class UserAddress(
    val addressId: Int,
    val userId: Int,
    val street: String,
    val city: String,
    val state: String,
    val zipCode: String
)