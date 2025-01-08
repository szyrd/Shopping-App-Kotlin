package com.example.shoppingapp.data

import android.content.Context
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString

object CategoryRepository {
    val categories = mutableListOf<Category>()

    fun loadCategoriesFromJson(context: Context) {
        val json = context.assets.open("categories.json").bufferedReader().use { it.readText() }
        val loadedCategories = Json.decodeFromString<List<Category>>(json)
        categories.addAll(loadedCategories)
    }
}
