package model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface BookingDao {
    // Insert a new booking
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBooking(booking: Booking)

    // Delete a booking
    @Delete
    suspend fun deleteBooking(booking: Booking)

    // Get all bookings
    @Transaction
    @Query("SELECT * FROM bookings")
    fun getAllBookings(): Flow<List<Booking>>

    // Helper function to get specific booking details
    @Query("SELECT * FROM bookings WHERE bookingId = :id")
    suspend fun getBookingById(id: Int): Booking?
}
