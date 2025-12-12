package com.example.tickety

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import model.getCurrentUserId // 1. لازم نعمل Import للدوال دي من الـ Model
import model.saveCurrentUserId // 1. لازم نعمل Import للدوال دي من الـ Model
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserSessionTest {

    @Test
    fun save_and_retrieve_user_id() {
        // 1. نجيب Context حقيقي
        val context = ApplicationProvider.getApplicationContext<Context>()

        // 2. نحفظ ID وهمي (نوعه Long عشان الدالة بتطلب Long)
        val fakeUserId = 123L
        saveCurrentUserId(context, fakeUserId)

        // 3. نسترجع الـ ID (اللي راجع نوعه Int)
        val retrievedId = getCurrentUserId(context)

        // 4. التصحيح هنا: حولنا الـ fakeUserId لـ Int عشان المقارنة تنجح
        assertEquals(fakeUserId.toInt(), retrievedId)
    }
}