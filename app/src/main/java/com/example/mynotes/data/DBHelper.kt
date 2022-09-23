package com.example.mynotes.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, "MY_NOTE.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE MY_NOTE(ID AUTO_INCREMENT PRIMARY KEY, NAME_NOTE TEXT, CONTENT TEXT, TIME_EDIT TEXT)")
        db?.execSQL("INSERT INTO MY_NOTE(NAME_NOTE, CONTENT) VALUES('Test', 'This is test my note',datetime('now', 'localtime'))")
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS MY_NOTE")
    }



}