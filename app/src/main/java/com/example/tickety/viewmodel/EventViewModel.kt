package com.example.tickety.viewmodel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import model.Event
import model.TicketsRepository

class EventViewModel(private val repository: TicketsRepository) : ViewModel() {

    // (Screen State)
    var uiState by mutableStateOf(EventsUiState())
        private set

    init {
        loadAllEvents()
    }

    private fun loadAllEvents() {
        viewModelScope.launch {
            repository.allEvents.collectLatest { events ->
                uiState = uiState.copy(eventsList = events)
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        uiState = uiState.copy(searchQuery = newQuery)
        viewModelScope.launch {
            if (newQuery.isBlank()) {
                repository.allEvents.collectLatest { events ->
                    uiState = uiState.copy(eventsList = events)
                }
            } else {
                repository.searchEvents(newQuery).collectLatest { filteredEvents ->
                    uiState = uiState.copy(eventsList = filteredEvents)
                }
            }
        }
    }

    suspend fun getEventById(id: Int): Event? {
        return repository.getEventById(id)
    }
}