package model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Event::class, Booking::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun eventDao(): EventDao
    abstract fun bookingDao(): BookingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "easy_tickets_db"
                )
                    // Add Callback to populate dummy data on creation
                    .addCallback(AppDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    // add initial dummy data in the database when its created
    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    populateDatabase(database.eventDao())
                }
            }
        }

        suspend fun populateDatabase(eventDao: EventDao) {
            // Dummy data
            val dummyEvents = listOf(
                Event(
                    title = "Amr Diab Concert",
                    date = "2023-12-15",
                    location = "Cairo Stadium",
                    price = 500.0,
                    description = "Mega concert for the superstar Amr Diab.",
                    imageUrl = "file:///android_asset/AmrDiabConcert.jpg"
                ),
                Event(
                    title = "Cairo International Book Fair",
                    date = "2024-01-25",
                    location = "Egypt Int. Exhibition Center",
                    price = 5.0,
                    description = "The biggest cultural event in the Middle East.",
                    imageUrl = "file:///android_asset/CairoInternationalBookFair.jpeg"
                ),
                Event(
                    title = "Al Ahly vs Zamalek",
                    date = "2023-11-30",
                    location = "Cairo Stadium",
                    price = 100.0,
                    description = "The classic derby match.",
                    imageUrl = "file:///android_asset/Al-AhlyvsZamalek.jpg"
                )
            )
            eventDao.insertAll(dummyEvents)
        }
    }
}
