package com.example.studentcontactapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.Switch
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.studentcontactapp.R
import com.example.studentcontactapp.ui.login.LoginActivity
import com.example.studentcontactapp.utils.PrefManager
import com.example.studentcontactapp.utils.SettingsManager

class ProfileFragment : Fragment() {

    private lateinit var settings: SettingsManager
    private lateinit var pref: PrefManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        settings = SettingsManager(requireContext())
        pref = PrefManager(requireContext())

        val switchDark = view.findViewById<Switch>(R.id.switchDark)
        val switchFont = view.findViewById<Switch>(R.id.switchFont)
        val switchNotif = view.findViewById<Switch>(R.id.switchNotif)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        // INIT STATE
        switchDark.isChecked = settings.isDarkMode()
        switchFont.isChecked = settings.isFontSize()
        switchNotif.isChecked = settings.isNotification()

        // 🌙 DARK MODE (TIDAK PINDAH TAB LAGI)
        switchDark.setOnCheckedChangeListener { _, isChecked ->
            settings.setDarkMode(isChecked)

            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

        // 🔠 FONT
        switchFont.setOnCheckedChangeListener { _, isChecked ->
            settings.setFontSize(isChecked)
        }

        // 🔔 NOTIF
        switchNotif.setOnCheckedChangeListener { _, isChecked ->
            settings.setNotification(isChecked)
        }

        // 🚪 LOGOUT (FIX)
        btnLogout.setOnClickListener {
            pref.logout()

            val intent = Intent(requireContext(), LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
