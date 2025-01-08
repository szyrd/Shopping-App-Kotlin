package com.example.shoppingapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "cart_items")
data class CartItem(
    @PrimaryKey(autoGenerate = true) val cartItemId: Int = 0,
    val cartId: Int,
    val productId: Int,
    var quantity: Int

): Parcelable

