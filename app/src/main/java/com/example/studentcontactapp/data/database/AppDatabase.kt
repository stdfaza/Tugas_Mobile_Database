package com.example.studentcontactapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.studentcontactapp.data.database.dao.StudentDao
import com.example.studentcontactapp.data.entity.StudentEntity

@Database(entities = [StudentEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "student_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}