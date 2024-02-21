package com.example.singandsongs.ui.home

import androidx.lifecycle.*
import com.example.singandsongs.data.CantoRepository
import com.example.singandsongs.data.PlayListRepository
import com.example.singandsongs.model.*
import com.example.singandsongs.utils.FilterCondition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val playListRepository: PlayListRepository,
    private val cantoRepository: CantoRepository
) : ViewModel() {

    private val _filterCondition = MutableLiveData<FilterCondition>().apply {
        value = FilterCondition.CANTOS
    }

    private val filterCondition: LiveData<FilterCondition> = _filterCondition

    var cantos: LiveData<List<Canto>> = filterCondition.switchMap { cantoRepository.getAllCantos(it).asLiveData() }
    val playListWithCantos: LiveData<PlayListWithCantos> = playListRepository.getPlayListWithCantos.asLiveData()

    val addCanto: (Int, String, Kind, String) -> Unit = { number, name, kind, text ->
        val canto = Canto(number, name, kind)
        viewModelScope.launch(Dispatchers.IO) {
            val cantoId = cantoRepository.insertCanto(canto)
            println("CANTO ID $cantoId")
            val content = Content(cantoId, text)
            cantoRepository.insertContent(content)
        }
    }

    val addDraft:  (Int, String, Kind, String) -> Unit = { number, name, kind, text ->
        val canto = Canto.createDraftCanto(name,number, kind)
        viewModelScope.launch(Dispatchers.IO) {
            cantoRepository.insertCanto(canto)
        }
    }

    val editCanto: (Int, String, Kind, String) -> Unit = { _, _, _, _ ->

    }

    fun deleteCanto(position: Int) {
        cantos.value?.get(position)?.let {
            viewModelScope.launch(Dispatchers.IO) {
                cantoRepository.deleteCanto(it)
            }
        }
    }
    fun checkFilterCondition(filterCondition: FilterCondition) {
        _filterCondition.value = filterCondition
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