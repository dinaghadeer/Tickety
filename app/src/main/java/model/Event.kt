package model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "events")
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val date: String,
    val location: String,
    val price: Double,
    val description: String,
    val imageUrl: Int // Can be a URL or a resource ID
)
