package model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "bookings",
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["id"],
            childColumns = ["eventId"],
            onDelete = ForeignKey.CASCADE // If an event is deleted, delete its bookings too
        )
    ]
)
data class Booking(
    @PrimaryKey(autoGenerate = true) val bookingId: Int = 0,
    val eventId: Int,
    val userId: Int,

    val quantity: Int,
    val bookingDate: String,

    val totalPrice: Double,
    val eventLocation: String,
    val eventTitle: String
)
