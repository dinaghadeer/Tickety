package com.example.tickety.viewmodel
import model.Event
data class EventsUiState (

    val eventsList: List<Event> = emptyList(),
    val searchQuery: String = ""


     )
