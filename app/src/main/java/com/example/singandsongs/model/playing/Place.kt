package com.example.singandsongs.model.playing

import android.location.Address
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


@Entity(tableName = "place_table")
data class Place (
  val name: String,
  //val address: Address,
  val hours: List<String>,
  @PrimaryKey(autoGenerate = true)
  val placeId: Long = 0
) {
  @Ignore
  val hoursText: String = hours.joinToString(", ")
}
