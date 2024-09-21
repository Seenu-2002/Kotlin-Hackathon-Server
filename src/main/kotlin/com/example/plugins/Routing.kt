package com.example.plugins

import com.example.database.repository.UserRepository
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
            val users = UserRepository.getUsers()
            call.respond(users)
        }

        post("/users") {
            val name = call.parameters["name"] ?: return@post call.respondText("Missing name", status = HttpStatusCode.BadRequest)
            val email = call.parameters["email"] ?: return@post call.respondText("Missing email", status = HttpStatusCode.BadRequest)
            val user = UserRepository.createOrGetUser(name, email)
            call.respond(user)
        }
    }
}
