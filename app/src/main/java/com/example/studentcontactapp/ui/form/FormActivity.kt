package com.example.studentcontactapp.ui.form

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.studentcontactapp.R
import com.example.studentcontactapp.data.database.AppDatabase
import com.example.studentcontactapp.data.entity.StudentEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormActivity : AppCompatActivity() {

    private var isEdit = false
    private var studentId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        val etName = findViewById<EditText>(R.id.etName)
        val etNim = findViewById<EditText>(R.id.etNim)
        val spinnerProdi = findViewById<Spinner>(R.id.spinnerProdi)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etSemester = findViewById<EditText>(R.id.etSemester)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)

        val dao = AppDatabase.getDatabase(this).studentDao()
        val prodiOptions = listOf("Informatika", "Sistem Informasi", "Teknik Komputer")

        spinnerProdi.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            prodiOptions
        ).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }

        // ambil data dari intent (kalau edit)
        isEdit = intent.getBooleanExtra("isEdit", false)
        studentId = intent.getIntExtra("id", 0)

        if (isEdit && studentId != 0) {
            lifecycleScope.launch(Dispatchers.IO) {
                val student = dao.getStudentById(studentId) ?: return@launch

                withContext(Dispatchers.Main) {
                    etName.setText(student.name)
                    etNim.setText(student.nim)
                    etEmail.setText(student.email)
                    etSemester.setText(student.semester)

                    val selectedIndex = prodiOptions.indexOf(student.prodi)
                    if (selectedIndex >= 0) {
                        spinnerProdi.setSelection(selectedIndex)
                    }
                }
            }
        }

        btnSimpan.setOnClickListener {

            val name = etName.text.toString().trim()
            val nim = etNim.text.toString().trim()
            val prodi = spinnerProdi.selectedItem.toString()
            val email = etEmail.text.toString().trim()
            val semester = etSemester.text.toString().trim()

            if (name.isEmpty() || nim.isEmpty() || email.isEmpty() || semester.isEmpty()) {
                Toast.makeText(this, "Semua field wajib diisi", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                if (isEdit) {
                    dao.update(
                        StudentEntity(
                            id = studentId,
                            name = name,
                            nim = nim,
                            prodi = prodi,
                            email = email,
                            semester = semester
                        )
                    )
                } else {
                    dao.insert(
                        StudentEntity(
                            name = name,
                            nim = nim,
                            prodi = prodi,
                            email = email,
                            semester = semester
                        )
                    )
                }

                finish()
            }
        }
    }
}
