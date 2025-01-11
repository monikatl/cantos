package com.example.singandsongs.data.place

import androidx.annotation.WorkerThread
import com.example.singandsongs.model.playing.Place
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlaceRepository @Inject constructor(
  private val placeDao: PlaceDao
)  {

  fun getAllPlaces() = placeDao.getAllPlaces()

  @WorkerThread
  suspend fun insertPlace(place: Place) = withContext(Dispatchers.IO) {
    placeDao.insertPlace(place)
  }

  @WorkerThread
  suspend fun deletePlace(place: Place) = withContext(Dispatchers.IO) {
    placeDao.deletePlace(place)
  }

}
