package com.example.shoppingapp.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.shoppingapp.R
import com.example.shoppingapp.data.User
import com.example.shoppingapp.data.UserRepository

class SignUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val etUsername = findViewById<EditText>(R.id.etNewUsername)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etPassword = findViewById<EditText>(R.id.etNewPassword)
        val btnCreateAccount = findViewById<Button>(R.id.btnCreateAccount)

        btnCreateAccount.setOnClickListener {
            val username = etUsername.text.toString()
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                // Generate a unique userId
                val userId = (UserRepository.users.maxOfOrNull { it.userId } ?: 0) + 1

                val newUser = User(
                    userId = userId, // Provide generated userId
                    username = username,
                    email = email,
                    passwordHash = password.reversed() // Simulated password hashing
                )

                UserRepository.addUser(newUser)

                Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show()

                // Go back to LoginActivity
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }



    }
}
