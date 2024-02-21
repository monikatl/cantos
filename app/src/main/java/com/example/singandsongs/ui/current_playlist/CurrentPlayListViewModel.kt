package com.example.singandsongs.ui.current_playlist

import android.content.DialogInterface
import androidx.lifecycle.*
import com.example.singandsongs.data.PlayListRepository
import com.example.singandsongs.model.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentPlayListViewModel @Inject constructor(
    private val playListRepository: PlayListRepository
): ViewModel() {

    val playList: LiveData<PlayList> = playListRepository.getCurrentPlayList.asLiveData()
    val playListAttached: LiveData<Boolean> = playListRepository.isAttached.asLiveData()
    val playListWithCantos: LiveData<PlayListWithCantos> = playListRepository.getPlayListWithCantos.asLiveData()

    fun deletePlayList(dialog: DialogInterface?) {
        playList.value?.let {
            viewModelScope.launch {
                playListRepository.deletePlaylist(it)
            }
        }
        dialog?.dismiss()
    }

    fun deleteCanto(canto: Canto) {
        playListWithCantos.value?.let {
            viewModelScope.launch {
                playListRepository.deleteCantoPlayListCrossRef(CantoPlayListCrossRef(canto.cantoId,playList.value?.playListId!!))
            }
        }
    }
}