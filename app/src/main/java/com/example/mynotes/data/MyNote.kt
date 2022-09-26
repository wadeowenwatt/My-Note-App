package com.example.mynotes.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "note")
data class MyNote(
//    @PrimaryKey(autoGenerate = true)
//    var id: Int = 0,
    @NonNull @PrimaryKey @ColumnInfo(name = "name")
    var nameNote: String,
    @ColumnInfo(name = "content")
    var content: String? = null,
    @ColumnInfo(name = "time")
    var timeEdit: String? = null
)
