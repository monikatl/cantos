package com.example.singandsongs.ui.notifications

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import androidx.lifecycle.LiveData
import com.example.singandsongs.data.PlayListRepository
import com.example.singandsongs.model.PlayList
import com.example.singandsongs.utils.SortCondition
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val playListRepository: PlayListRepository
) : ViewModel() {



    private val _sortCondition = MutableLiveData<SortCondition>().apply {
        value = SortCondition.AZ
    }

    val sortCondition: LiveData<SortCondition> = _sortCondition


    @RequiresApi(Build.VERSION_CODES.O)
    var playLists: LiveData<List<PlayList>> = sortCondition.switchMap { playListRepository.getAllPlayLists(it).asLiveData() }


    @RequiresApi(Build.VERSION_CODES.O)
    val addNewPlayList: (LocalDate, String, Boolean) -> Unit = { date, name, isDefault ->
        val playList = PlayList(date, name, isDefault)
        playLists.value?.let {
            viewModelScope.launch(Dispatchers.IO) {
                if(isDefault)
                    it.filter { item -> item.isCurrent }.map { item -> item.disconnectPlayList() }.forEach { item -> playListRepository.editPlayList(item) }
                playListRepository.insertPlayList(playList)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun setCurrentPlayList(position: Int): PlayList? {
        var playList: PlayList? = null
        playLists.value?.let{
            playList = it[position]
            playList?.let { pl ->
                pl.setCurrent()
                it.filter { item -> item.playListId != pl.playListId }.map { item -> item.disconnectPlayList() }
                viewModelScope.launch(Dispatchers.IO) {
                    it.forEach { item -> playListRepository.editPlayList(item) }
                }
            }
        }
        return playList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun choseSortCondition(condition: SortCondition) {
        _sortCondition.value = condition
    }
}