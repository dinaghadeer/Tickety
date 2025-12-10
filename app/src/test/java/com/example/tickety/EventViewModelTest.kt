package com.example.tickety
import com.example.tickety.viewmodel.EventViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import model.Event
import model.TicketsRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class EventViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var repository: TicketsRepository

    private lateinit var viewModel: EventViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `loadEvents updates uiState with data from repository`() = runTest {

        val dummyEvents = listOf(
            Event(id = 1, title = "Event 1", date = "2023", location = "Cairo", price = 100.0, description = "", imageUrl = 0),
            Event(id = 2, title = "Event 2", date = "2024", location = "Giza", price = 200.0, description = "", imageUrl = 0)
        )

        whenever(repository.allEvents).thenReturn(flowOf(dummyEvents))

        // Act
        viewModel = EventViewModel(repository)

        assertEquals(dummyEvents, viewModel.uiState.eventsList)
    }

    @Test
    fun `search logic updates uiState correctly`() = runTest {
        // Arrange
        val allEvents = listOf(
            Event(id = 1, title = "Cinema Movie", date = "", location = "", price = 0.0, description = "", imageUrl = 0),
            Event(id = 2, title = "Football Match", date = "", location = "", price = 0.0, description = "", imageUrl = 0)
        )
        val searchResults = listOf(allEvents[0])


        whenever(repository.allEvents).thenReturn(flowOf(allEvents))
        whenever(repository.searchEvents("Cinema")).thenReturn(flowOf(searchResults))

        viewModel = EventViewModel(repository)

        viewModel.onSearchQueryChanged("Cinema")

        // Assert
        assertEquals("Cinema", viewModel.uiState.searchQuery)
        assertEquals(searchResults, viewModel.uiState.eventsList)
    }
}