package com.example.tickety.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import model.Booking
import model.TicketsRepository

class BookingViewModel(private val repository: TicketsRepository) : ViewModel() {

    val myBookings: StateFlow<List<Booking>> = repository.myBookings
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addBooking(booking: Booking) {
        viewModelScope.launch {
            repository.insertBooking(booking)
        }
    }

    fun cancelBooking(booking: Booking) {
        viewModelScope.launch {
            repository.deleteBooking(booking)
        }
    }
}