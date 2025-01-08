package com.example.shoppingapp.data

import android.content.Context
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

object ProductRepository {
    fun loadProductsFromJson(context: Context): List<Product> {
        val json = context.assets.open("products.json").bufferedReader().use { it.readText() }
        return Json.decodeFromString(json)
    }
}
