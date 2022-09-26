package com.example.mynotes.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: MyNote)

    @Update
    suspend fun update(note: MyNote)

    @Delete
    suspend fun delete(note: MyNote)

//    @Query("SELECT * FROM note WHERE id = :id")
//    fun getItem(id: Int): Flow<MyNote>
}