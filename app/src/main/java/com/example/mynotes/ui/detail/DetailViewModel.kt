package com.example.mynotes.ui.detail

import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailViewModel : ViewModel() {

    private val _saveBtn = MutableLiveData<FloatingActionButton>()
    val saveBtn: LiveData<FloatingActionButton> = _saveBtn

    fun checkEmpty(
        editNameNote: EditText,
        editNote: EditText,
        btnSave: FloatingActionButton
    ) {
        _saveBtn.value = btnSave
        if (editNameNote.text.isNotEmpty() or editNote.text.isNotEmpty()) {
            _saveBtn.value!!.isEnabled = true
        }
    }

}