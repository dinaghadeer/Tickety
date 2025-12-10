package com.example.tickety.model
import com.example.tickety.viewmodel.BookingViewModel
import com.example.tickety.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import model.Booking
import model.TicketsRepository
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class BookingViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: TicketsRepository

    private lateinit var viewModel: BookingViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)

        whenever(repository.myBookings).thenReturn(flowOf(emptyList()))
        viewModel = BookingViewModel(repository)
    }

    @Test
    fun `addBooking calls repository insert`() = runTest {
        // Arrange
        val newBooking = Booking(eventId = 1, quantity = 2, bookingDate = "2023", totalPrice = 200.0, eventLocation = "", eventTitle = "")

        // Act
        viewModel.addBooking(newBooking)

        // Assert

        verify(repository).insertBooking(newBooking)
    }
    @Test
    fun `cancelBooking calls repository delete`() = runTest {

        val booking = Booking(bookingId = 1, eventId = 1, quantity = 1, bookingDate = "2023", totalPrice = 100.0, eventLocation = "", eventTitle = "")

        // Act:
        viewModel.cancelBooking(booking)

        // Assert
        verify(repository).deleteBooking(booking)
    }



}