package com.example.singandsongs.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.singandsongs.data.place.PlaceRepository
import com.example.singandsongs.data.playlist.PlayListRepository
import com.example.singandsongs.model.playing.Place
import com.example.singandsongs.model.playlist.PlayList
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class PlayingViewModel @Inject constructor(
  private val placeRepository: PlaceRepository,
  private val playListRepository: PlayListRepository
) : ViewModel() {

  var places: LiveData<List<Place>> = placeRepository.getAllPlaces().asLiveData()
  var playLists: LiveData<List<PlayList>> = playListRepository.getAllPlayLists().asLiveData()



}
