package model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface EventDao {
    //insert an Event object
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvent(event: Event)

    // Get all events (Returns Flow to update UI automatically)
    @Query("SELECT * FROM events")
    fun getAllEvents(): Flow<List<Event>>

    // Search for an event by title
    @Query("SELECT * FROM events WHERE title LIKE '%' || :searchQuery || '%'")
    fun searchEvents(searchQuery: String): Flow<List<Event>>

    // Get a single event by ID
    @Query("SELECT * FROM events WHERE id = :id")
    suspend fun getEventById(id: Int): Event?

    // Insert a list of events (Used for dummy data)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(events: List<Event>)
}
