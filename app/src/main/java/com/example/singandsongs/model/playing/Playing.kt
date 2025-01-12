package com.example.singandsongs.model.playing

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate


@Entity(tableName = "playing_table")
data class Playing(
  val data: LocalDate,
  val name: String,
  val placeId: Long,
  val time: String,
  val playListId: Long,
  @PrimaryKey(autoGenerate = true)
  val playingId: Long = 0
)
