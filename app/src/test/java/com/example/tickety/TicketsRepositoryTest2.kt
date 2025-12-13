package com.example.tickety.model

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
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class TicketsRepositoryTest2 {

    @Mock
    private lateinit var eventDao: EventDao
    @Mock
    private lateinit var bookingDao: BookingDao

    private lateinit var repository: TicketsRepository

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)


        whenever(eventDao.getAllEvents()).thenReturn(flowOf(emptyList()))

        whenever(bookingDao.getAllBookings(any())).thenReturn(flowOf(emptyList()))

        repository = TicketsRepository(eventDao, bookingDao)
    }

    @Test
    fun insertEvent_calls_dao_insert() = runTest {
        // Arrange
        val event = Event(id = 1, title = "Test", date = "2024", location = "Loc", price = 10.0, description = "Desc", imageUrl = 0)

        // Act
        repository.insertEvent(event)


        verify(eventDao).insertEvent(event)
    }

    @Test
    fun insertBooking_calls_dao_insert() = runTest {
        // Arrange

        val booking = Booking(eventId = 1, userId = 10, quantity = 2, bookingDate = "2024", totalPrice = 20.0, eventLocation = "Loc", eventTitle = "Title")

        // Act
        repository.insertBooking(booking)

        // Assert
        verify(bookingDao).insertBooking(booking)
    }

    @Test
    fun deleteBooking_calls_dao_delete() = runTest {
        val booking = Booking(bookingId=1, eventId = 1, userId = 10, quantity = 1, bookingDate = "2024", totalPrice = 10.0, eventLocation = "Loc", eventTitle = "Title")

        repository.deleteBooking(booking)

        verify(bookingDao).deleteBooking(booking)
    }
}