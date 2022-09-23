package com.example.mynotes.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mynotes.data.DBHelper

class ActivityViewModel : ViewModel() {
    private var _db = MutableLiveData<DBHelper>()
    var db : LiveData<DBHelper> = _db


}