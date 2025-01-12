package com.example.singandsongs.data.day

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.singandsongs.model.playing.Day
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {
  @Query("SELECT * FROM day_table")
  fun getAllDays(): Flow<List<Day>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertDay(day: Day): Long

  @Delete
  fun deleteDay(day: Day)
}
