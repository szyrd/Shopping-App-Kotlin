package com.example.shoppingapp.data

class ReviewRepository {

    // Temporary storage for reviews
    private val reviews = mutableListOf<Review>()

    // Add or update a review
    fun submitReview(review: Review) {
        val existingReviewIndex = reviews.indexOfFirst {
            it.productId == review.productId && it.userId == review.userId
        }
        if (existingReviewIndex != -1) {
            // Update the existing review
            reviews[existingReviewIndex] = review
        } else {
            // Add a new review
            reviews.add(review)
        }
    }

    // Retrieve a review for a specific product and user
    fun getReview(productId: Int, userId: Int): Review? {
        return reviews.find { it.productId == productId && it.userId == userId }
    }

    // List all reviews
    fun getAllReviews(): List<Review> = reviews.toList()
}


