package com.example.studentcontactapp.ui.home

import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.R
import com.example.studentcontactapp.data.database.AppDatabase
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var adapter: StudentAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val etSearch = view.findViewById<EditText>(R.id.etSearch)

        adapter = StudentAdapter(requireContext()) {
            loadData() // sekarang aman
        }

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        loadData()

        etSearch.addTextChangedListener {
            val dao = AppDatabase.getDatabase(requireContext()).studentDao()

            viewLifecycleOwner.lifecycleScope.launch {
                val result = if (it.isNullOrEmpty()) {
                    dao.getAllStudents()
                } else {
                    dao.searchStudents(it.toString())
                }
                adapter.submitList(result)
            }
        }
    }

    // 🔥 PINDAHKAN KE SINI
    private fun loadData() {
        val dao = AppDatabase.getDatabase(requireContext()).studentDao()

        viewLifecycleOwner.lifecycleScope.launch {
            val data = dao.getAllStudents()
            adapter.submitList(data)
        }
    }
}
