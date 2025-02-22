package com.example.singandsongs.ui.home

import androidx.lifecycle.*
import com.example.singandsongs.data.canto.CantoRepository
import com.example.singandsongs.data.playlist.PlayListRepository
import com.example.singandsongs.model.playlist.Canto
import com.example.singandsongs.model.playlist.CantoPlayListCrossRef
import com.example.singandsongs.model.playlist.Content
import com.example.singandsongs.model.playlist.Kind
import com.example.singandsongs.model.playlist.PlayListWithCantos
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
    var drafts: LiveData<List<Canto>> =  cantoRepository.getAllCantos(FilterCondition.DRAFTS).asLiveData()
    private var contents: LiveData<List<Content>> = cantoRepository.getAllContents().asLiveData()
    val playListWithCantos: LiveData<PlayListWithCantos> = playListRepository.getPlayListWithCantos.asLiveData()


    val addCanto: (Int, String, Kind, Int, String, Canto?) -> Unit = { number, name, kind, pageCounter, text, _ ->
        val canto = Canto(number, name, kind, pageCounter)
        viewModelScope.launch(Dispatchers.IO) {
            val cantoId = cantoRepository.insertCanto(canto)
            val content = Content(cantoId, text)
            cantoRepository.insertContent(content)
        }
    }

    val addDraft:  (Int, String, Kind, Int, String, Canto?) -> Unit = { number, name, kind, pageCounter, text, _ ->
        val canto = Canto.createDraftCanto(name,number, kind, pageCounter)
        viewModelScope.launch(Dispatchers.IO) {
            val cantoId = cantoRepository.insertCanto(canto)
            val content = Content(cantoId, text)
            cantoRepository.insertContent(content)
        }
    }

    val editCanto: (Int, String, Kind, Int, String, Canto?) -> Unit = { number, name, kind, pageCounter, text, canto ->
        canto?.let{
            it.number = number
            it.name = name
            it.kind = kind
            it.sheetCounter = pageCounter
            val content = contents.value?.first { c ->  c.cantoId == it.cantoId   }
            content?.text = text
            viewModelScope.launch {
                cantoRepository.updateCanto(it)
                content?.let {content ->
                    cantoRepository.updateContent(content)
                }
            }
        }
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

    val addCantoToCurrentPlayList: (Long, Int) -> Unit = { cantoId, currentCounter ->
        val playListId = playListWithCantos.value?.playList?.playListId
        playListId?.let {
            viewModelScope.launch(Dispatchers.IO) {
                playListRepository.insertCantoPlayListCrossRef(CantoPlayListCrossRef(cantoId, it))
                val canto = cantos.value?.find { it.cantoId == cantoId }
                canto?.let {
                  it.currentSheetCount = currentCounter
                  cantoRepository.updateCanto(it)
                }
            }
        }
    }


    fun transformDraft(canto: Canto){
        canto.transformDraft()
        viewModelScope.launch(Dispatchers.IO) {
            cantoRepository.updateCanto(canto)
        }
    }

    fun undoAddDraftToCantos(canto: Canto) {
        if(!canto.isDraft) transformDraft(canto)
    }

    val checkFav: (Canto) -> Unit = { canto ->
        canto.checkAsFavourite()
        viewModelScope.launch(Dispatchers.IO) {
            cantoRepository.updateCanto(canto)
        }

    }
}
