package com.example.studentcontactapp.utils

import android.content.Context

class SettingsManager(context: Context) {

    private val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)

    fun setDarkMode(value: Boolean) {
        prefs.edit().putBoolean("dark", value).apply()
    }

    fun isDarkMode(): Boolean {
        return prefs.getBoolean("dark", false)
    }

    fun setFontSize(value: Boolean) {
        prefs.edit().putBoolean("font", value).apply()
    }

    fun isFontSize(): Boolean {
        return prefs.getBoolean("font", false)
    }

    fun setNotification(value: Boolean) {
        prefs.edit().putBoolean("notif", value).apply()
    }

    fun isNotification(): Boolean {
        return prefs.getBoolean("notif", true)
    }

    fun logout() {
        prefs.edit().clear().apply()
    }
}