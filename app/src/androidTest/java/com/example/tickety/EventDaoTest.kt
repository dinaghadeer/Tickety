package com.example.tickety

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

// ⚠️ أهم جزء: لازم نعمل Import للكلاسات بتاعتك من الباكدج model
import model.AppDatabase
import model.Event
import model.EventDao

@RunWith(AndroidJUnit4::class)
class EventDaoTest {

    // تعريف المتغيرات عشان نقدر نستخدمها في كل الدوال
    private lateinit var db: AppDatabase
    private lateinit var eventDao: EventDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        // إنشاء داتابيز مؤقتة في الذاكرة (In-Memory)
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
            .allowMainThreadQueries() // السماح بتشغيلها على الـ Main Thread عشان التيست
            .build()

        eventDao = db.eventDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetEvent() = runBlocking {
        // 1. Arrange (تجهيز)
        val event = Event(
            title = "Amr Diab Concert",
            date = "2024",
            location = "Cairo",
            price = 500.0,
            description = "Party",
            imageUrl = 123
        )

        // 2. Act (تنفيذ)
        eventDao.insertEvent(event)

        // 3. Assert (تحقق)
        // دالة first() محتاجة import kotlinx.coroutines.flow.first
        val allEvents = eventDao.getAllEvents().first()

        assertEquals(1, allEvents.size)
        assertEquals("Amr Diab Concert", allEvents[0].title)
    }
}