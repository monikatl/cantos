package com.example.singandsongs.data

import androidx.room.*
import com.example.singandsongs.model.Canto
import com.example.singandsongs.model.Kind
import kotlinx.coroutines.flow.Flow

@Dao
interface CantoDao {
    @Query("SELECT * FROM canto_table WHERE is_draft = false")
    fun getAllCantos(): Flow<List<Canto>>

    @Query("SELECT * FROM canto_table WHERE is_draft = true")
    fun getAllDrafts(): Flow<List<Canto>>

    @Query("SELECT * FROM canto_table WHERE is_favourite = true")
    fun getAllFavourites(): Flow<List<Canto>>

    @Query("SELECT * FROM canto_table WHERE kind = :kind")
    fun getAllCantosByKind(kind: Kind): Flow<List<Canto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCanto(canto: Canto)

    @Update
    fun updateCanto(canto: Canto)

    @Delete
    fun deleteCanto(canto: Canto)
}