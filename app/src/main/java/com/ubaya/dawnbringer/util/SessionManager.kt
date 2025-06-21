package com.ubaya.dawnbringer.util

import android.content.Context

class SessionManager(context: Context) {
    private val prefs = context.getSharedPreferences("session", Context.MODE_PRIVATE)

    fun save(username: String) {
        prefs.edit().putString("logged_user", username).apply()
    }
    fun getUsername(): String? {
        return prefs.getString("logged_user", null)
    }
    fun saveBudgetId(id: Int) {
        prefs.edit().putInt("selected_budget_id", id).apply()
    }

    fun getBudgetId(): Int? {
        val id = prefs.getInt("selected_budget_id", -1)
        return if (id != -1) id else null
    }


    fun get(): String? = prefs.getString("logged_user", null)

    fun clear() {
        prefs.edit().clear().apply()
    }

}
