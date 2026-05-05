package com.example.studentcontactapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.studentcontactapp.ui.home.HomeFragment
import com.example.studentcontactapp.ui.profile.ProfileFragment
import com.example.studentcontactapp.utils.SettingsManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var settings: SettingsManager
    private var currentMenuId = R.id.nav_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 INIT SETTINGS
        settings = SettingsManager(this)

        // 🌙 APPLY DARK MODE SAAT APP START
        if (settings.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        setContentView(R.layout.activity_main)


        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        if (savedInstanceState != null) {
            currentMenuId = savedInstanceState.getInt("menu", R.id.nav_home)
        }

        bottomNav.selectedItemId = currentMenuId

        // 🔥 DEFAULT FRAGMENT (HOME)
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, HomeFragment())
            .commit()

        // 🔥 HANDLE NAVIGATION
        bottomNav.setOnItemSelectedListener { item ->
            currentMenuId = item.itemId

            val fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> HomeFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()

            true
        }

    }
}