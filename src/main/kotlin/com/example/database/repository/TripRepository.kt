package com.example.database.repository

import com.example.database.tables.Trip
import com.example.database.tables.Trips
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object TripRepository {

    fun getAllTrips(): List<Trip> {
        return transaction {
            Trips.selectAll().map {
                Trip(
                    it[Trips.id].value,
                    it[Trips.car],
                    it[Trips.fromDestination],
                    it[Trips.toDestination],
                    it[Trips.date],
                    it[Trips.time],
                    it[Trips.totalSeats],
                    it[Trips.filled],
                    it[Trips.participants],
                    it[Trips.owner],
                )
            }
        }
    }

    fun insertTrip(
        car: String,
        fromDestination: String,
        toDestination: String,
        date: String,
        time: String,
        totalSeats: Int,
        filled: Int,
        participants: String,
        owner: Int
    ): Trip {
        var tripId: Int = 0
        transaction {
            tripId = Trips.insertAndGetId {
                it[Trips.car] = car
                it[Trips.fromDestination] = fromDestination
                it[Trips.toDestination] = toDestination
                it[Trips.date] = date
                it[Trips.time] = time
                it[Trips.totalSeats] = totalSeats
                it[Trips.filled] = filled
                it[Trips.participants] = participants
                it[Trips.owner] = owner
            }.value
        }
        return Trip(tripId, car, fromDestination, toDestination, date, time, totalSeats, filled, participants, owner)
    }


}