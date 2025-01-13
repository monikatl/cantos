package com.example.singandsongs.model.playing

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.Month

@Entity(tableName = "day_table")
data class Day @RequiresApi(Build.VERSION_CODES.O) constructor(
  val number: Int = 1,
  val month: String = "",
  val year: Int = 2025,
  val date: LocalDate = LocalDate.now(),
  var playings: List<Long> = emptyList(),
  @PrimaryKey(autoGenerate = true)
  val dayId: Long = 0
) {

  fun addPlaying(playingId: Long) {
    playings = playings.toMutableList().apply { add(playingId) }
  }


  companion object {
    @RequiresApi(Build.VERSION_CODES.O)
    fun create(number: Int, month: String, year: Int): Day {
      val monthType = Month.valueOf(month.uppercase())
      val date = LocalDate.of(year, monthType, number)
      return Day(number, month, year, date)
    }
  }
}
