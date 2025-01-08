package com.example.shoppingapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
@Entity(tableName = "products")
data class Product(
    @PrimaryKey val productId: Int,
    val name: String,
    val description: String,
    val price: Double,
    val imageUrl: String,
    val category: String
) : Parcelable {
    companion object {
        fun fromJson(json: String): List<Product> {
            val type = object : TypeToken<List<Product>>() {}.type
            return Gson().fromJson(json, type)
        }
    }
}
