package com.example.singandsongs.model.playing

import androidx.room.Entity
import com.example.singandsongs.model.playlist.PlayList
import java.time.LocalDate


@Entity(tableName = "playing_table")
data class Playing(
  val data: LocalDate,
  val name: String,
  val place: Place,
  val event: Event,
  val playList: PlayList
)
