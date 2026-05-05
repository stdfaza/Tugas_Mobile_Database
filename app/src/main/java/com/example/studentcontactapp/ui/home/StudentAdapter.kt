package com.example.studentcontactapp.ui.home

import android.app.AlertDialog
import android.content.Context
import android.view.*
import android.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.R
import com.example.studentcontactapp.data.database.AppDatabase
import com.example.studentcontactapp.data.entity.StudentEntity
import kotlinx.coroutines.*

class StudentAdapter(
    private val context: Context,
    private val onDelete: () -> Unit
) : RecyclerView.Adapter<StudentAdapter.ViewHolder>() {

    private var list = listOf<StudentEntity>()

    fun submitList(newList: List<StudentEntity>) {
        list = newList
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvName: TextView = view.findViewById(R.id.tvName)
        val btnDelete: Button = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = list[position]
        holder.tvName.text = student.name

        val dao = AppDatabase.getDatabase(context).studentDao()

        holder.btnDelete.setOnClickListener {
            AlertDialog.Builder(context)
                .setTitle("Hapus Data")
                .setMessage("Yakin hapus?")
                .setPositiveButton("Ya") { _, _ ->
                    CoroutineScope(Dispatchers.IO).launch {
                        dao.deleteById(student.id)
                        withContext(Dispatchers.Main) {
                            onDelete()
                        }
                    }
                }
                .setNegativeButton("Batal", null)
                .show()
        }
    }
}