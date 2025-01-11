package com.example.singandsongs.ui.calendar.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.singandsongs.data.place.PlaceRepository
import com.example.singandsongs.model.playing.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
  private val placeRepository: PlaceRepository
): ViewModel() {
  var places: LiveData<List<Place>> = placeRepository.getAllPlaces().asLiveData()
}
