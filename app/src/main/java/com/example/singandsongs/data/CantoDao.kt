package com.example.singandsongs.data

import androidx.room.*
import com.example.singandsongs.model.Canto
import kotlinx.coroutines.flow.Flow

@Dao
interface CantoDao {
    @Query("SELECT * FROM canto_table")
    fun getAllCantos(): Flow<List<Canto>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCanto(canto: Canto)

    @Delete
    fun deleteCanto(canto: Canto)
}