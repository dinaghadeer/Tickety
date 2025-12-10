//package com.example.tickety
package com.example.tickety.model

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import model.Booking
import model.BookingDao
import model.Event
import model.EventDao
import model.TicketsRepository
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class TicketsRepositoryTest {


    @Mock
    private lateinit var eventDao: EventDao
    @Mock
    private lateinit var bookingDao: BookingDao


    private lateinit var repository: TicketsRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        whenever(eventDao.getAllEvents()).thenReturn(flowOf(emptyList()))
        whenever(bookingDao.getAllBookings()).thenReturn(flowOf(emptyList()))

        // ربط الـ Repository بالـ Mocks
        repository = TicketsRepository(eventDao, bookingDao)
    }

    @Test
    fun `insertEvent calls dao insertEvent`() = runTest {
        // Arrange
        val event = Event(id = 1, title = "Test Event", date = "", location = "", price = 10.0, description = "", imageUrl = 0)

        // Act
        repository.insertEvent(event)


        verify(eventDao).insertEvent(event)
    }

    @Test
    fun `searchEvents delegates to dao`() = runTest {
        // Arrange
        val query = "Concert"
        val expectedList = listOf(Event(id = 1, title = "Concert 1", date = "", location = "", price = 10.0, description = "", imageUrl = 0))
        whenever(eventDao.searchEvents(query)).thenReturn(flowOf(expectedList))

        // Act
        repository.searchEvents(query)

        // Assert
        verify(eventDao).searchEvents(query)
    }

    @Test
    fun `insertBooking calls dao insertBooking`() = runTest {
        // Arrange
        val booking = Booking(eventId = 1, quantity = 2, bookingDate = "2023", totalPrice = 20.0, eventLocation = "", eventTitle = "")

        // Act
        repository.insertBooking(booking)

        // Assert
        verify(bookingDao).insertBooking(booking)
    }
    @Test
    fun `deleteBooking calls dao deleteBooking`() = runTest {
        // Arrange
        val booking = Booking(bookingId = 1, eventId = 1, quantity = 1, bookingDate = "2023", totalPrice = 100.0, eventLocation = "", eventTitle = "")

        // Act
        repository.deleteBooking(booking)

        // Assert
        verify(bookingDao).deleteBooking(booking)
    }




}