package com.example.mynotes.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM note ORDER BY name ASC")
    fun getNotes(): Flow<List<MyNote>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(note: MyNote)

    @Update
    suspend fun update(note: MyNote)

    @Delete
    suspend fun delete(note: MyNote)

    @Query("SELECT * FROM note WHERE id = :id")
    fun getNote(id: Int): Flow<MyNote>
}