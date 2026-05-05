package com.example.studentcontactapp.utils

import android.content.Context

class PrefManager(context: Context) {

    private val prefs = context.getSharedPreferences("login", Context.MODE_PRIVATE)

    fun setLogin(username: String) {
        prefs.edit()
            .putBoolean("isLogin", true)
            .putString("username", username)
            .apply()
    }

    fun isLogin(): Boolean {
        return prefs.getBoolean("isLogin", false)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}