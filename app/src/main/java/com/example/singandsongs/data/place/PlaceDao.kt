package com.example.singandsongs.data.place

import androidx.room.Dao
import androidx.room.Query
import com.example.singandsongs.model.playing.Place
import kotlinx.coroutines.flow.Flow

@Dao
interface PlaceDao {
  @Query("SELECT * FROM place_table")
  fun getAllPlaces(): Flow<List<Place>>
}
