package com.example.studentcontactapp.utils

import android.content.Context

class PrefManager(context: Context) {

    private val prefs = context.getSharedPreferences("app_pref", Context.MODE_PRIVATE)

    fun setLogin(username: String) {
        prefs.edit().putBoolean("isLogin", true)
            .putString("username", username)
            .apply()
    }

    fun isLogin(): Boolean = prefs.getBoolean("isLogin", false)

    fun getUsername(): String? = prefs.getString("username", "")

    fun logout() {
        prefs.edit().clear().apply()
    }
}