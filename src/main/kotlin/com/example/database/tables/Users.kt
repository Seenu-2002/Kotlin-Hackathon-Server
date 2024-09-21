package com.example.database.tables

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

object Users : IntIdTable() {

    val name = varchar("name", 255)
    val email = varchar("email", 255)


}

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String
)