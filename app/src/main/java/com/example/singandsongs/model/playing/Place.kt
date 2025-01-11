package com.example.singandsongs.model.playing

import android.location.Address
import androidx.room.Entity


@Entity(tableName = "place_table")
data class Place (
  val name: String,
  val address: Address,
  val events: Event
)
