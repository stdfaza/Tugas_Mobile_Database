package com.example.studentcontactapp.ui.search

import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.studentcontactapp.R

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val text = view.findViewById<TextView>(R.id.tvWelcome)
        text.text = "Search Page"

        return view
    }
}