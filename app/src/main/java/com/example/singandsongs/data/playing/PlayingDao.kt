package com.example.singandsongs.data.playing

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.singandsongs.model.playing.Playing
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayingDao {
  @Query("SELECT * FROM playing_table")
  fun getAllPlayings(): Flow<List<Playing>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertPlaying(playing: Playing): Long

  @Delete
  fun deletePlaying(playing: Playing)
}
