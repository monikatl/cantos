package com.example.singandsongs.ui.current_playlist

import android.content.Context
import android.content.DialogInterface
import androidx.lifecycle.*
import com.example.benedictus.src.main.java.Set
import com.example.singandsongs.data.CantoRepository
import com.example.singandsongs.data.PlayListRepository
import com.example.singandsongs.model.*
import com.example.singandsongs.utils.FilterCondition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CurrentPlayListViewModel @Inject constructor(
    private val playListRepository: PlayListRepository,
    private val cantoRepository: CantoRepository
): ViewModel() {

    private val _id = MutableLiveData<Long>().apply {
        value = 0
    }
    val id: LiveData<Long> = _id

    private val _cardIsActive = MutableLiveData<Boolean>().apply {
      value = false
    }
    var cardIsActive: LiveData<Boolean> = _cardIsActive

    val playList: LiveData<PlayList> = playListRepository.getCurrentPlayList.asLiveData()
    val playListAttached: LiveData<Boolean> = playListRepository.isAttached.asLiveData()
    val playListWithCantos: LiveData<PlayListWithCantos> = playListRepository.getPlayListWithCantos.asLiveData()
    val cantoContent: LiveData<CantoAndContent> = id.switchMap { cantoRepository.getCantoAndContent(it).asLiveData()  }
    val cantosAndContents: LiveData<List<CantoAndContent>> = cantoRepository.getCantosAndContents().asLiveData()
    var cantos: LiveData<List<Canto>> = cantoRepository.getAllCantos(FilterCondition.CANTOS).asLiveData()

    val isQueueMode: Boolean = true

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
    fun setCurrentCanto(id: Long) {
        _id.value = id
    }

    fun resolveAction(id: Long, currentCounter: Int) {
      val canto = cantos.value?.find { it.cantoId == id }
      canto?.let {
        it.currentSheetCount = currentCounter
        viewModelScope.launch {
          cantoRepository.updateCanto(it)
        }
      }
      setCurrentCanto(id)
    }


    fun addContentToCanto(cantoAndContent: CantoAndContent) {
        viewModelScope.launch {
            cantoRepository.updateCanto(cantoAndContent.canto)
            cantoAndContent.content?.let {
                cantoRepository.updateContent(Content(cantoAndContent.canto.cantoId, "ttttt"))
            }
        }
    }

    fun isActive() {
      _cardIsActive.value = true
    }
    fun isInactive() {
      _cardIsActive.value = false
    }

    fun sendSetToSD(context: Context) {
      val playList = playList.value
      val mapCantos = playListWithCantos.value?.cantos?.associate { it.number to it.currentSheetCount }
      playList?.let {
        val set = Set("999" + ".txt", playList.name, mapCantos)
        set.exportToTXT(context)
      }

    }
}
