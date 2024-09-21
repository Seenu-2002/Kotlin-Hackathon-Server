package com.example.database.repository

import com.example.database.tables.User
import com.example.database.tables.Users
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object UserRepository {

    fun getUsers(): List<User> {
        return transaction {
            Users.selectAll().map {
                User(it[Users.id].value, it[Users.name], it[Users.email])
            }
        }
    }

    fun createOrGetUser(name: String, email: String): User {
        var userId: Int = 0
        getUsers().find { it.name == name && it.email == email }?.let {
            return it
        }
        transaction {
            userId = Users.insertAndGetId {
                it[Users.name] = name
                it[Users.email] = email
            }.value
        }
        return User(userId, name, email)
    }

}