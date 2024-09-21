package com.example.plugins

import com.example.database.tables.getAllUsers
import com.example.database.tables.insertUser
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        get("/") {
            call.respondText("Hello World!")
        }
        get("/users") {
            call.respond(getAllUsers().toString())
        }

        post("/users") {
            val name = call.parameters["name"] ?: return@post call.respondText("Missing name", status = HttpStatusCode.BadRequest)
            val email = call.parameters["email"] ?: return@post call.respondText("Missing email", status = HttpStatusCode.BadRequest)
            val user = insertUser(name, email)
            call.respond(user)
        }
    }
}
