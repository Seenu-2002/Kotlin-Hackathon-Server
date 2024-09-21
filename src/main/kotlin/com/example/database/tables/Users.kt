package com.example.database.tables

import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Users : IntIdTable() {

    val name = varchar("name", 255)
    val email = varchar("email", 255)


}
data class User(
    val id: Int,
    val name: String,
    val email: String
)

fun insertUser(name: String, email: String): User {
    var userId: Int = 0
    transaction {
        userId = Users.insertAndGetId {
            it[Users.name] = name
            it[Users.email] = email
        }.value
    }
    return User(userId, name, email)
}

fun getAllUsers(): List<User> {
    return transaction {
        Users.selectAll().map {
            User(it[Users.id].value, it[Users.name], it[Users.email])
        }
    }
}