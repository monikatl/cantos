package com.example.singandsongs.ui.calendar.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.singandsongs.data.place.PlaceRepository
import com.example.singandsongs.data.playing.PlayingRepository
import com.example.singandsongs.data.playlist.PlayListRepository
import com.example.singandsongs.model.playing.FullPlaying
import com.example.singandsongs.model.playing.Place
import com.example.singandsongs.model.playing.Playing
import com.example.singandsongs.model.playlist.PlayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayingListViewModel @Inject constructor(
  private val placeRepository: PlaceRepository,
  private val playListRepository: PlayListRepository,
  private val playingRepository: PlayingRepository
) : ViewModel() {

  val playings: LiveData<List<Playing>> = playingRepository.getAllPlayings().asLiveData()
  val places: LiveData<List<Place>> = placeRepository.getAllPlaces().asLiveData()
  val playLists: LiveData<List<PlayList>> = playListRepository.getAllPlayLists().asLiveData()

  val playingList: MediatorLiveData<List<FullPlaying>> = MediatorLiveData<List<FullPlaying>>().apply {
    addSource(playings) { updatePlayingList() }
    addSource(places) { updatePlayingList() }
    addSource(playLists) { updatePlayingList() }
  }

  fun updatePlayingList(dayList: List<Playing>? = null) {
    val currentPlayings = dayList ?: playings.value ?: emptyList()
    val currentPlaces = places.value ?: emptyList()
    val currentPlayLists = playLists.value ?: emptyList()

    val fullPlayings = currentPlayings.map { playing ->
      createFullPlaying(playing, currentPlaces, currentPlayLists)
    }
    playingList.value = fullPlayings
  }

  private fun createFullPlaying(
    playing: Playing,
    currentPlaces: List<Place>,
    currentPlayLists: List<PlayList>
  ): FullPlaying {
    val place = currentPlaces.find { it.placeId == playing.placeId }
    val playList = currentPlayLists.find { it.playListId == playing.playListId }
    return FullPlaying(playing.data, playing.name, place, playing.time, playList, playing)
  }

  fun deletePlaying(playing: Playing) {
    viewModelScope.launch {
      playingRepository.deletePlaying(playing)
    }
  }
}


