package com.example.shoppingapp.data

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.serialization.json.Json
import kotlinx.serialization.decodeFromString


object UserRepository {
    private var userList: List<User> = emptyList()
    val users = mutableListOf<User>()

    fun loadUsersFromJson(context: Context) {
        val json = context.assets.open("users.json").bufferedReader().use { it.readText() }
        val loadedUsers = Json.decodeFromString<List<User>>(json)
        users.addAll(loadedUsers)
    }

    fun authenticate(username: String, passwordHash: String): User? {
        return userList.find { it.username == username && it.passwordHash == passwordHash }
    }

    fun addUser(user: User) {
        users.add(user)
    }

    fun userExists(username: String, email: String): Boolean {
        return userList.any { it.username == username || it.email == email }
    }

    // Validate credentials
    fun validateUser(username: String, password: String): Boolean {
        val hashedPassword = password.reversed() // Simulate hashing as a String
        return users.any { it.username == username && it.passwordHash == hashedPassword }
    }


}
