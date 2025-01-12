package com.example.singandsongs.model.playing

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "day_table")
data class Day (
  val number: Int = 1,
  val monthNumber: Int = 1,
  val year: Int = 2025,
  val playings: List<Long> = emptyList(),
  @PrimaryKey(autoGenerate = true)
  val dayId: Long = 0
)
