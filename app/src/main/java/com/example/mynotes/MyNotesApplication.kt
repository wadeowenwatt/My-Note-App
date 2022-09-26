package com.example.mynotes

import android.app.Application
import com.example.mynotes.data.NoteRoomDatabase

class MyNotesApplication : Application() {
    val database: NoteRoomDatabase by lazy { NoteRoomDatabase.getDatabase(this) }
}