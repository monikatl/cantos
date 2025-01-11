package com.example.singandsongs.model.playing

import androidx.room.Entity

@Entity(tableName = "day_table")
data class Day (
  val playing: List<Playing>
)
