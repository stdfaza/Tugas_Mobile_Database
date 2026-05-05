package com.example.studentcontactapp.utils

import android.content.Context

object FileHelper {

    fun saveNote(context: Context, nim: String, content: String) {
        context.openFileOutput("note_$nim.txt", Context.MODE_PRIVATE)
            .use { it.write(content.toByteArray()) }
    }

    fun loadNote(context: Context, nim: String): String {
        return try {
            context.openFileInput("note_$nim.txt")
                .bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            ""
        }
    }

    fun deleteNote(context: Context, nim: String) {
        context.deleteFile("note_$nim.txt")
    }

    fun isNoteExists(context: Context, nim: String): Boolean {
        return context.fileList().contains("note_$nim.txt")
    }
}