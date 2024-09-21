package com.example.database.tables


import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.id.IntIdTable

object Trips : IntIdTable() {

    val car = varchar("car",255)
    val fromDestination =  varchar("from_destination",255)
    val toDestination = varchar("to_destination",255)
    val date = varchar("date",255)
    val time = varchar("time",255)
    val totalSeats = integer("total_seats")
    val filled = integer("filled")
    val participants = varchar("participants",255)
    val owner = integer("owner")

}

@Serializable
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


