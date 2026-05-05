package com.example.studentcontactapp.ui.home

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.R
import com.example.studentcontactapp.data.database.AppDatabase
import com.example.studentcontactapp.data.entity.StudentEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StudentAdapter(
    private val context: Context,
    private val onDataChanged: () -> Unit
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    private var list = listOf<StudentEntity>()

    fun submitList(data: List<StudentEntity>) {
        list = data
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvAvatar: TextView = view.findViewById(R.id.tvAvatar)
        val btnDelete: Button? = view.findViewById(R.id.btnDelete) // optional
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = list[position]

        // Set nama
        holder.tvName.text = student.name

        // Generate avatar (inisial)
        val initials = student.name.split(" ")
            .map { it.first().toString() }
            .take(2)
            .joinToString("")
        holder.tvAvatar.text = initials

        // OPTIONAL: warna beda-beda biar keren
        val colors = listOf(
            "#5C6BC0", // biru
            "#E53935", // merah
            "#00897B", // hijau
            "#FBC02D"  // kuning
        )
        val color = android.graphics.Color.parseColor(colors[position % colors.size])
        holder.tvAvatar.setBackgroundColor(color)

        holder.btnDelete?.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Hapus Data")
                .setMessage("Yakin ingin menghapus ${student.name}?")
                .setPositiveButton("Ya") { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        val dao = AppDatabase.getDatabase(context).studentDao()
                        dao.deleteById(student.id)

                        // refresh di main thread
                        CoroutineScope(Dispatchers.Main).launch {
                            onDataChanged()
                        }
                    }
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}