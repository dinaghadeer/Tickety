package model

import kotlinx.coroutines.flow.Flow

class TicketsRepository(private val eventDao: EventDao, private val bookingDao: BookingDao) {

    // --- Events Operations ---
    val allEvents: Flow<List<Event>> = eventDao.getAllEvents()

    suspend fun insertEvent(event: Event) {
        eventDao.insertEvent(event)
    }

    fun searchEvents(query: String): Flow<List<Event>> {
        return eventDao.searchEvents(query)
    }

    suspend fun getEventById(eventId: Int): Event? {
        return eventDao.getEventById(eventId)
    }

    // --- Booking Operations ---
    //val myBookings: Flow<List<Booking>> = bookingDao.getAllBookings()
    fun getAllBookings(userId: Int): Flow<List<Booking>> {
        return bookingDao.getBookingsForUser(userId)
    }

    suspend fun insertBooking(booking: Booking) {
        bookingDao.insertBooking(booking)
    }

    suspend fun deleteBooking(booking: Booking) {
        bookingDao.deleteBooking(booking)
    }
}
