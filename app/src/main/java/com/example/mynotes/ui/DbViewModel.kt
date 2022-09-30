package com.example.mynotes.ui

import androidx.lifecycle.*
import com.example.mynotes.data.MyNote
import com.example.mynotes.data.NoteDao
import kotlinx.coroutines.launch

class DbViewModel(private val noteDao: NoteDao) : ViewModel() {

    val allNotes: LiveData<List<MyNote>> = noteDao.getNotes().asLiveData()

    fun changeDeleteMode() {
        for (i in allNotes.value!!.indices) {
            updateNote(
                allNotes.value!![i].id,
                allNotes.value!![i].nameNote,
                allNotes.value!![i].content!!,
                allNotes.value!![i].timeEdit!!,
                1
            )
        }
    }
    fun backDefaultMode() {
        for (i in allNotes.value!!.indices) {
            updateNote(
                allNotes.value!![i].id,
                allNotes.value!![i].nameNote,
                allNotes.value!![i].content!!,
                allNotes.value!![i].timeEdit!!,
                0
            )
        }
    }

    fun confirmDeleteMode(note: MyNote) {
        for (i in allNotes.value!!.indices) {
            if (note.id == allNotes.value!![i].id) {
                updateNote(
                    allNotes.value!![i].id,
                    allNotes.value!![i].nameNote,
                    allNotes.value!![i].content!!,
                    allNotes.value!![i].timeEdit!!,
                    2
                )
            }
        }
    }

    fun acceptDeleteMode(note: MyNote) {
        deleteNote(note)
    }

    fun denyDeleteMode(note: MyNote) {
        for (i in allNotes.value!!.indices) {
            if (note.id == allNotes.value!![i].id) {
                updateNote(
                    allNotes.value!![i].id,
                    allNotes.value!![i].nameNote,
                    allNotes.value!![i].content!!,
                    allNotes.value!![i].timeEdit!!,
                    1
                )
            }
        }
    }

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

//    private fun updateViewType(note: MyNote) {
//        viewModelScope.launch {
//            noteDao.update(note)
//        }
//    }

    private fun getNewNoteEntry(
        nameNote: String,
        content: String,
        timeEdit: String,
        viewType: Int
    ): MyNote {
        return MyNote(
            nameNote = nameNote,
            content = content,
            timeEdit = timeEdit,
            viewType = viewType
        )
    }

    private fun getUpdatedNoteEntry(
        noteId: Int,
        nameNote: String,
        content: String,
        timeEdit: String,
        viewType: Int
    ): MyNote {
        return MyNote(
            id = noteId,
            nameNote = nameNote,
            content = content,
            timeEdit = timeEdit,
            viewType = viewType
        )
    }

    fun addNewNote(
        nameNote: String,
        content: String,
        timeEdit: String,
        viewType: Int
    ) {
        val newNote = getNewNoteEntry(nameNote, content, timeEdit, viewType)
        insertNote(newNote)
    }

    fun updateNote(
        noteId: Int,
        nameNote: String,
        content: String,
        timeEdit: String,
        viewType: Int
    ) {
        val noteUpdated =
            getUpdatedNoteEntry(noteId, nameNote, content, timeEdit, viewType)
        updateNote(noteUpdated)
    }

    fun deleteNote(
        noteId: Int,
        nameNote: String,
        content: String,
        timeEdit: String,
        viewType: Int
    ) {
        val noteDelete =
            getUpdatedNoteEntry(noteId, nameNote, content, timeEdit, viewType)
        deleteNote(noteDelete)
    }

    fun searchNote(searchQuery: String): LiveData<List<MyNote>> {
        return noteDao.searchNote(searchQuery).asLiveData()
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