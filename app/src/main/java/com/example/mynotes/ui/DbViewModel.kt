package com.example.mynotes.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.mynotes.data.MyNote
import com.example.mynotes.data.NoteDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class DbViewModel(private val noteDao: NoteDao) : ViewModel() {
    private fun insertNote(note: MyNote) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    private fun getNewNoteEntry(
        nameNote: String,
        content: String,
        timeEdit: String
    ): MyNote {
        return MyNote(
            nameNote = nameNote,
            content = content,
            timeEdit = timeEdit
        )
    }

    fun addNewNote(
        nameNote: String,
        content: String,
        timeEdit: String
    ) {
        val newNote = getNewNoteEntry(nameNote, content, timeEdit)
        insertNote(newNote)
    }

    fun isEntryValid(
        nameNote: String,
        content: String,
        timeEdit: String
    ): Boolean {
        if (nameNote.isBlank() || content.isBlank() || timeEdit.isBlank()) {
            return false
        }
        return true
    }
}

class DbViewModelFactory(private val noteDao: NoteDao) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DbViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DbViewModel(noteDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}