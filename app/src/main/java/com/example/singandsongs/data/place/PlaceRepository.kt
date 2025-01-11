package com.example.singandsongs.data.place

import com.example.singandsongs.data.playlist.PlayListDao
import javax.inject.Inject

class PlaceRepository @Inject constructor(
  private val placeDao: PlaceDao
)  {

  fun getAllPlaces() = placeDao.getAllPlaces()
}
