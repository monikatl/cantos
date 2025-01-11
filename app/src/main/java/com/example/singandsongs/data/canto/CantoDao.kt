package com.example.singandsongs.data.canto

import androidx.room.*
import com.example.singandsongs.model.playlist.Canto
import com.example.singandsongs.model.playlist.CantoAndContent
import com.example.singandsongs.model.playlist.Content
import com.example.singandsongs.model.playlist.Kind
import kotlinx.coroutines.flow.Flow

@Dao
interface CantoDao {
    @Query("SELECT * FROM canto_table WHERE is_draft = false ORDER BY cantoId DESC")
    fun getAllCantos(): Flow<List<Canto>>

    @Query("SELECT * FROM canto_table WHERE is_draft = true ORDER BY cantoId DESC")
    fun getAllDrafts(): Flow<List<Canto>>

    @Query("SELECT * FROM canto_table WHERE is_favourite = true")
    fun getAllFavourites(): Flow<List<Canto>>

    @Query("SELECT * FROM canto_table WHERE kind = :kind")
    fun getAllCantosByKind(kind: Kind): Flow<List<Canto>>

    @Transaction
    @Query("SELECT * FROM canto_table WHERE cantoId = :id LIMIT 1")
    fun getCantoAndContent(id: Long): Flow<CantoAndContent>

    @Query("SELECT * FROM content_table")
    fun getAllContents(): Flow<List<Content>>

    @Transaction
    @Query("SELECT * FROM canto_table")
    fun getCantosAndContents(): Flow<List<CantoAndContent>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCanto(canto: Canto): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertContent(content: Content)

    @Update
    fun updateCanto(canto: Canto)

    @Delete
    fun deleteCanto(canto: Canto)

    @Update
    fun updateContent(content: Content)
}
