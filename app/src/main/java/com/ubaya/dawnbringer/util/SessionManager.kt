package com.ubaya.dawnbringer.util

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun save(username: String) {
        prefs.edit().putString("logged_user", username).apply()
    }

    fun get(): String? = prefs.getString("logged_user", null)

    fun clear() {
        prefs.edit().clear().apply()
    }
}
