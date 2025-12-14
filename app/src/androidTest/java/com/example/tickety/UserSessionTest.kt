package com.example.tickety

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import model.getCurrentUserId
import model.saveCurrentUserId
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserSessionTest {

    @Test
    fun save_and_retrieve_user_id() {

        val context = ApplicationProvider.getApplicationContext<Context>()


        val fakeUserId = 123L
        saveCurrentUserId(context, fakeUserId)


        val retrievedId = getCurrentUserId(context)


        assertEquals(fakeUserId.toInt(), retrievedId)
    }
}