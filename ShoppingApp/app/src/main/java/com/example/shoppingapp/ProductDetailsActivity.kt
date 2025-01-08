package com.example.shoppingapp.ui

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.shoppingapp.R
import com.example.shoppingapp.data.Product

class ProductDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_details)

        val product = intent.getParcelableExtra<Product>("product") ?: return

        val productName: TextView = findViewById(R.id.productName)
        val productPrice: TextView = findViewById(R.id.productPrice)
        val productDescription: TextView = findViewById(R.id.productDescription)
        val productImage: ImageView = findViewById(R.id.productImage)
        val addToCartButton: Button = findViewById(R.id.addToCartButton)

        // Set product details
        productName.text = product.name
        productPrice.text = "Price: \$${product.price}"
        productDescription.text = product.description
        Glide.with(this).load(product.imageUrl).into(productImage)

        addToCartButton.setOnClickListener {
            Toast.makeText(this, "${product.name} added to cart!", Toast.LENGTH_SHORT).show()
        }
    }
}




