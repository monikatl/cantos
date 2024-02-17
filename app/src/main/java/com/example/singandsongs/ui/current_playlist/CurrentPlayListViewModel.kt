package com.example.singandsongs.ui.current_playlist

import android.content.DialogInterface
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.singandsongs.data.PlayListRepository
import com.example.singandsongs.model.PlayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentPlayListViewModel @Inject constructor(
    private val playListRepository: PlayListRepository
): ViewModel() {

    val playList: LiveData<PlayList> = playListRepository.getCurrentPlayList.asLiveData()
    val playListAttached: LiveData<Boolean> = playListRepository.isAttached.asLiveData()
    @RequiresApi(Build.VERSION_CODES.O)
    val all: LiveData<List<PlayList>> = playListRepository.getAllPlayLists.asLiveData()

    fun deletePlayList(dialog: DialogInterface?) {
        playList.value?.let {
            viewModelScope.launch {
                playListRepository.deletePlaylist(it)
            }
        }
        dialog?.dismiss()
    }
}