package com.example.singandsongs.model.playing

import androidx.room.Entity


@Entity(tableName = "event_table")
data class Event (
  val name: String,
  val hour: String
)
