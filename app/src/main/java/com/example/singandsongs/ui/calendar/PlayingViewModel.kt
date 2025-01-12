package com.example.singandsongs.ui.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.singandsongs.data.place.PlaceRepository
import com.example.singandsongs.data.playing.PlayingRepository
import com.example.singandsongs.data.playlist.PlayListRepository
import com.example.singandsongs.model.playing.Place
import com.example.singandsongs.model.playing.Playing
import com.example.singandsongs.model.playlist.PlayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class PlayingViewModel @Inject constructor(
  private val placeRepository: PlaceRepository,
  private val playListRepository: PlayListRepository,
  private val playingRepository: PlayingRepository
) : ViewModel() {

  var places: LiveData<List<Place>> = placeRepository.getAllPlaces().asLiveData()
  var playLists: LiveData<List<PlayList>> = playListRepository.getAllPlayLists().asLiveData()

  fun addPlaying(playing: Playing) {
    viewModelScope.launch {
      playingRepository.insertPlaying(playing)
    }
  }
}
