package com.example.singandsongs.ui.calendar.places

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.singandsongs.data.place.PlaceRepository
import com.example.singandsongs.model.playing.Place
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlacesViewModel @Inject constructor(
  private val placeRepository: PlaceRepository
): ViewModel() {
  var places: LiveData<List<Place>> = placeRepository.getAllPlaces().asLiveData()

  val addPlace: (String, List<String>) -> Unit = { name, hours ->
    val place = Place(name, hours)
    viewModelScope.launch(Dispatchers.IO) {
      placeRepository.insertPlace(place)
    }
  }

  val deletePlace: (Place) -> Unit = { place ->
    viewModelScope.launch {
      placeRepository.deletePlace(place)
    }
  }
}
