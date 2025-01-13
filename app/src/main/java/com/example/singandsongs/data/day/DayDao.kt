package com.example.singandsongs.data.day

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.singandsongs.model.playing.Day
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface DayDao {
  @Query("SELECT * FROM day_table")
  fun getAllDays(): Flow<List<Day>>

  @Query("SELECT * FROM day_table WHERE date = :date")
  suspend fun getDayByDate(date: LocalDate?): Day?

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  suspend fun insertDay(day: Day): Long

  @Update
  suspend fun updateDay(day: Day)

  @Delete
  suspend fun deleteDay(day: Day)
}
