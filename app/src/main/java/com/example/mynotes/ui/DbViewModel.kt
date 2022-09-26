package com.example.mynotes.ui

import androidx.lifecycle.*
import com.example.mynotes.data.MyNote
import com.example.mynotes.data.NoteDao
import kotlinx.coroutines.launch

class DbViewModel(private val noteDao: NoteDao) : ViewModel() {

    val allNotes: LiveData<List<MyNote>> = noteDao.getNotes().asLiveData()

    private fun insertNote(note: MyNote) {
        viewModelScope.launch {
            noteDao.insert(note)
        }
    }

    private fun updateNote(note: MyNote) {
        viewModelScope.launch {
            noteDao.update(note)
        }
    }

    private fun deleteNote(note: MyNote) {
        viewModelScope.launch {
            noteDao.delete(note)
        }
    }

    private fun getNote(id: Int) {
        viewModelScope.launch {
            noteDao.getNote(id)
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

    private fun getUpdatedNoteEntry(
        noteId: Int,
        nameNote: String,
        content: String,
        timeEdit: String
    ): MyNote {
        return MyNote(
            id = noteId,
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

    fun updateNote(
        noteId: Int,
        nameNote: String,
        content: String,
        timeEdit: String
    ) {
        val noteUpdated = getUpdatedNoteEntry(noteId, nameNote, content, timeEdit)
        updateNote(noteUpdated)
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