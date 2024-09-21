package com.example.plugins

import com.example.database.repository.TripRepository
import com.example.database.repository.UserRepository
import com.example.database.tables.Trips.integer
import com.example.database.tables.Trips.varchar
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

        get("/trips"){
            val trips = TripRepository.getAllTrips()
            call.respond(trips)
        }

        post("/trip/add"){
            val car = call.parameters["car"] ?: return@post call.respondText("Missing Car", status = HttpStatusCode.BadRequest)
            val fromDestination = call.parameters["fromDestination"] ?: return@post call.respondText("Missing Pick-up Location", status = HttpStatusCode.BadRequest)
            val toDestination = call.parameters["toDestination"] ?: return@post call.respondText("Missing Destination", status = HttpStatusCode.BadRequest)
            val date = call.parameters["date"] ?: return@post call.respondText("Missing Date", status = HttpStatusCode.BadRequest)
            val time = call.parameters["time"] ?: return@post call.respondText("Missing Time", status = HttpStatusCode.BadRequest)
            val totalSeats = call.parameters["totalSeats"] ?: return@post call.respondText("Missing Total number of seats", status = HttpStatusCode.BadRequest)
            val filled = call.parameters["filled"]?.toIntOrNull() ?: 1
            val participants = call.parameters["participants"] ?: return@post call.respondText("Missing Participants", status = HttpStatusCode.BadRequest)
            val owner = call.parameters["owner"] ?: return@post call.respondText("Missing Owner", status = HttpStatusCode.BadRequest)
            if (filled > totalSeats.toInt() || filled < 1) {
                return@post call.respondText("'filled' seats cannot be greater than 'totalSeats'", status = HttpStatusCode.BadRequest)
            }
            val trip = TripRepository.insertTrip(car,fromDestination,toDestination,date,time,totalSeats.toInt(),filled,participants,owner.toInt())
            call.respond(trip)
        }
    }
}
