package com.example.singandsongs.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.singandsongs.data.CantoRepository
import com.example.singandsongs.data.PlayListRepository
import com.example.singandsongs.model.*
import com.google.android.material.tabs.TabLayout.Tab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playListRepository: PlayListRepository,
    private val cantoRepository: CantoRepository
) : ViewModel() {

    var cantos: LiveData<List<Canto>> = cantoRepository.getAllCantos.asLiveData()
    val playListWithCantos: LiveData<PlayListWithCantos> = playListRepository.getPlayListWithCantos.asLiveData()

    val addCanto: (Int, String, Kind) -> Unit = { number, name, kind ->
        val canto = Canto(number, name, kind)
        viewModelScope.launch(Dispatchers.IO) {
            cantoRepository.insertCanto(canto)
        }
    }

    fun deleteCanto(position: Int) {
        cantos.value?.get(position)?.let {
            viewModelScope.launch(Dispatchers.IO) {
                cantoRepository.deleteCanto(it)
            }
        }
    }

    fun filterCantosList(group: String) {

    }

    fun filterCantos(tab: Tab): List<Canto>? {
        return when(tab.text) {
            "Wszystkie" -> cantos.value
            "Ulubione" -> cantos.value?.filter { it.isFavourite }
            "Oczekujące" -> emptyList()
            else -> emptyList()
        }
    }


    val addCantoToCurrentPlayList: (Long) -> Unit = { cantoId ->
        val playListId = playListWithCantos.value?.playList?.playListId
        playListId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                playListRepository.insertCantoPlayListCrossRef(CantoPlayListCrossRef(cantoId, it))
            }
        }
    }

    val checkFav: (Canto) -> Unit = { canto ->
        canto.checkAsFavourite()
        viewModelScope.launch(Dispatchers.IO) {
            cantoRepository.updateCanto(canto)
        }

    }
}