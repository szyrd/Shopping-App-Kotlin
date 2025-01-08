package com.example.shoppingapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.shoppingapp.data.Product

class ProductViewModel : ViewModel() {
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    fun setProducts(productList: List<Product>) {
        _products.value = productList
    }

    fun filterProducts(category: String) {
        val allProducts = _products.value ?: return
        _products.value = if (category == "All Categories") {
            allProducts
        } else {
            allProducts.filter { it.category == category }
        }
    }
}
