package com.example.studentcontactapp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.studentcontactapp.ui.home.HomeFragment
import com.example.studentcontactapp.ui.login.LoginActivity
import com.example.studentcontactapp.ui.profile.ProfileFragment
import com.example.studentcontactapp.ui.search.SearchFragment
import com.example.studentcontactapp.utils.PrefManager
import com.example.studentcontactapp.utils.SettingsManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var settings: SettingsManager
    private lateinit var pref: PrefManager
    private var currentMenuId = R.id.nav_home

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 🔥 CHECK LOGIN
        pref = PrefManager(this)
        if (!pref.isLogin()) {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // 🔥 DARK MODE GLOBAL
        settings = SettingsManager(this)
        if (settings.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        // 🔥 RESTORE TAB
        if (savedInstanceState != null) {
            currentMenuId = savedInstanceState.getInt("menu", R.id.nav_home)
        }

        bottomNav.selectedItemId = currentMenuId

        // 🔥 NAVIGATION
        bottomNav.setOnItemSelectedListener { item ->
            currentMenuId = item.itemId

            val fragment = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_search -> SearchFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> HomeFragment()
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit()

            true
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("menu", currentMenuId)
    }
}
