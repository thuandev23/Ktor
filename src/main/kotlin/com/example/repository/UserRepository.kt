package com.example.repository

import com.example.model.User
import java.util.*

class UserRepository {
    private val users = mutableListOf(
        User(
            id = UUID.randomUUID(),
            username = "ADMIN",
            password = "ADMIN",
            role = "ADMIN"
        )
    )

    fun findAll(): List<User> = users

    fun findById(id: UUID): User? = users.firstOrNull { it.id == id }

    fun findByUsername(username: String): User? = users.firstOrNull { it.username == username }

    fun save(user: User) {
        users.add(user)
    }
}