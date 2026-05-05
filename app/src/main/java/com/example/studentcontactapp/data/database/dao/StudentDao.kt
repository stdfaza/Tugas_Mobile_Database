package com.example.studentcontactapp.data.database.dao

import androidx.room.*
import com.example.studentcontactapp.data.entity.StudentEntity

@Dao
interface StudentDao {

    @Insert
    suspend fun insert(student: StudentEntity)

    @Insert
    suspend fun insertAll(list: List<StudentEntity>)

    @Query("SELECT * FROM students ORDER BY name ASC")
    suspend fun getAllStudents(): List<StudentEntity>

    @Query("SELECT * FROM students WHERE id = :id")
    suspend fun getStudentById(id: Int): StudentEntity?

    @Query("SELECT * FROM students WHERE name LIKE '%' || :keyword || '%' OR nim LIKE '%' || :keyword || '%'")
    suspend fun searchStudents(keyword: String): List<StudentEntity>

    @Update
    suspend fun update(student: StudentEntity)

    @Query("DELETE FROM students WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT COUNT(*) FROM students")
    suspend fun getStudentCount(): Int
}