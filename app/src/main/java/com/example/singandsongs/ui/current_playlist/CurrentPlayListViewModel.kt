package com.example.singandsongs.ui.current_playlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import com.example.singandsongs.data.PlayListRepository
import com.example.singandsongs.model.PlayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

@HiltViewModel
class CurrentPlayListViewModel @Inject constructor(
    private val playListRepository: PlayListRepository
): ViewModel() {
    val playList: LiveData<PlayList> = playListRepository.getCurrentPlayList.asLiveData()
}