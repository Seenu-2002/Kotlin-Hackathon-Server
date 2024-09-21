package com.example.database.tables


import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

object Trips : IntIdTable() {

    val car = varchar("car",255)
    val fromDestination =  varchar("from_destination",255)
    val toDestination = varchar("to_destination",255)
    val date = varchar("data",255)
    val time = varchar("time",255)
    val totalSeats = integer("total_seats")
    val filled = integer("filled")
    val participants = varchar("participants",255)
    val owner = integer("owner")

}

data class Trip(
    val id : Int,
    val car : String,
    val fromDestination : String,
    val toDestination : String,
    val date : String,
    val time : String,
    val totalSeats : Int,
    val filled : Int,
    val participants : String,
    val owner : Int,
)

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