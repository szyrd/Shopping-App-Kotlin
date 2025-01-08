package com.example.shoppingapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.shoppingapp.adapters.ProductAdapter
import com.example.shoppingapp.data.AppDatabase
import com.example.shoppingapp.data.CartItem
import com.example.shoppingapp.data.CategoryRepository
import com.example.shoppingapp.data.Product
import com.example.shoppingapp.data.ProductRepository
import com.example.shoppingapp.databinding.ActivityMainBinding
import com.example.shoppingapp.ui.theme.CartActivity
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val mainScope = CoroutineScope(Dispatchers.Main)

    private val database by lazy { AppDatabase.getDatabase(this) }
    private val productDao by lazy { database.productDao() }
    private val cartItemDao by lazy { database.cartItemDao() }

    private lateinit var productAdapter: ProductAdapter
    private var allProducts: List<Product> = listOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadCategories()
        loadProducts()

        binding.viewCartButton.setOnClickListener {
            Toast.makeText(this, "Cart Activity Launching...", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, CartActivity::class.java))
        }
    }

    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        productAdapter = ProductAdapter(emptyList()) { product -> addToCart(product) }
        binding.recyclerView.adapter = productAdapter
    }

    private fun loadCategories() {
        // Load categories dynamically
        CategoryRepository.loadCategoriesFromJson(this)
        val categories = listOf("All Categories") + CategoryRepository.categories.map { it.categoryName }

        val spinnerAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerCategories.adapter = spinnerAdapter

        binding.spinnerCategories.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: android.view.View?, position: Int, id: Long) {
                val selectedCategory = categories[position]
                filterProducts(selectedCategory)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                filterProducts("All Categories")
            }
        }
    }

    private fun loadProducts() {
        mainScope.launch {
            val products = withContext(Dispatchers.IO) {
                val loadedProducts = ProductRepository.loadProductsFromJson(this@MainActivity)
                productDao.insertAll(loadedProducts)
                productDao.getAllProducts()
            }
            allProducts = products
            productAdapter.updateProducts(products)
        }
    }

    private fun filterProducts(category: String) {
        val filteredProducts = if (category == "All Categories") {
            allProducts
        } else {
            allProducts.filter { it.category == category }
        }
        productAdapter.updateProducts(filteredProducts)
    }

    private fun addToCart(product: Product) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                val cartItem = CartItem(cartId = 1, productId = product.productId, quantity = 1)
                cartItemDao.insertCartItem(cartItem)
            }
            Toast.makeText(this@MainActivity, "${product.name} added to cart", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }
}











