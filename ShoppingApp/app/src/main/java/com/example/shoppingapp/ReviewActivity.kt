package com.example.shoppingapp

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingapp.data.Review
import com.example.shoppingapp.data.ReviewRepository

class ReviewActivity : AppCompatActivity() {

    private lateinit var ratingBar: RatingBar
    private lateinit var commentEditText: EditText
    private lateinit var submitReviewButton: Button
    private lateinit var reviewTextView: TextView

    private val reviewRepository = ReviewRepository()
    private var productId: Int = -1
    private var userId: Int = 1 // Static userId; replace with logic to retrieve actual userId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        ratingBar = findViewById(R.id.ratingBar)
        commentEditText = findViewById(R.id.commentEditText)
        submitReviewButton = findViewById(R.id.submitReviewButton)
        reviewTextView = findViewById(R.id.reviewTextView)

        productId = intent.getIntExtra("productId", -1)

        // Check if a review exists
        val existingReview = reviewRepository.getReview(productId, userId)
        if (existingReview != null) {
            showExistingReview(existingReview)
        } else {
            enableReviewInput()
        }

        submitReviewButton.setOnClickListener {
            val review = Review(
                reviewId = System.currentTimeMillis().toInt(),
                productId = productId,
                userId = userId,
                rating = ratingBar.rating,
                comment = commentEditText.text.toString()
            )
            reviewRepository.submitReview(review)
            showExistingReview(review)
        }
    }

    private fun showExistingReview(review: Review) {
        reviewTextView.text = "Rating: ${review.rating}\nComment: ${review.comment}"
        reviewTextView.visibility = View.VISIBLE

        ratingBar.visibility = View.GONE
        commentEditText.visibility = View.GONE
        submitReviewButton.visibility = View.GONE
    }

    private fun enableReviewInput() {
        reviewTextView.visibility = View.GONE

        ratingBar.visibility = View.VISIBLE
        commentEditText.visibility = View.VISIBLE
        submitReviewButton.visibility = View.VISIBLE
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> { // Handle back button in the ActionBar
                onBackPressedDispatcher.onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}



