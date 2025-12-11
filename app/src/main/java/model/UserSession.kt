package model

import android.content.Context

fun saveCurrentUserId(context: Context, userId: Long){
    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    prefs.edit().putInt("current_user_id", userId.toInt()).apply()
}

fun getCurrentUserId(context: Context): Int {
    val prefs = context.getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
    return prefs.getInt("current_user_id", -1) // -1 means no user logged in
}
