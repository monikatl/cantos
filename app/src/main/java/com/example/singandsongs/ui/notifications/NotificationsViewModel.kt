package com.example.singandsongs.ui.notifications

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.singandsongs.R
import com.example.singandsongs.data.playlist.PlayListRepository
import com.example.singandsongs.data.PreferencesManager
import com.example.singandsongs.model.playlist.PlayList
import com.example.singandsongs.model.playlist.PlayListWithCantos
import com.example.singandsongs.utils.SortCondition
import com.example.singandsongs.utils.SortCondition.AZ
import com.example.singandsongs.utils.SortCondition.BY_ID
import com.example.singandsongs.utils.SortCondition.DATE_ASC
import com.example.singandsongs.utils.SortCondition.DATE_DESC
import com.example.singandsongs.utils.SortCondition.FREQ_ASC
import com.example.singandsongs.utils.SortCondition.FREQ_DESC
import com.example.singandsongs.utils.SortCondition.ZA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import java.time.LocalDate
import javax.inject.Inject

@RequiresApi(Build.VERSION_CODES.O)
@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val playListRepository: PlayListRepository,
    private val preferences: PreferencesManager
) : ViewModel() {


    private val _id = MutableLiveData<Long>().apply {
        value = 0
    }
    val id: LiveData<Long> = _id
    val checkIfIdIsNotZero = id.value!= 0L

    val isQueueActive: LiveData<Boolean> = preferences.enableQueueFlow.asLiveData()

    private val _sortCondition = MutableLiveData<SortCondition>().apply {
        value = SortCondition.AZ
    }
    private val sortCondition: LiveData<SortCondition> = _sortCondition

    var playLists: LiveData<List<PlayList>> = sortCondition.switchMap { playListRepository.getAllPlayLists(it).asLiveData() }
    var playListWithCantos: LiveData<PlayListWithCantos> = id.switchMap {  playListRepository.getPlayListWithCantosById(it).asLiveData() }

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

    fun choseSortCondition(checkedId: Int) {
      val condition = resolveOrder(checkedId)
      _sortCondition.value = condition
    }

    private fun resolveOrder(checkedId: Int) =
      when(checkedId) {
        R.id.a_z -> AZ
        R.id.z_a -> ZA
        R.id.freqAsc -> FREQ_ASC
        R.id.freqDesc -> FREQ_DESC
        R.id.dateAsc -> DATE_ASC
        R.id.dateDesc -> DATE_DESC
        else -> BY_ID
      }

    fun setChosenPlayListId(id: Long){
        _id.value = id
    }

    fun activateQueueSet() {
        viewModelScope.launch {
          preferences.setEnableQueue(true)
        }
    }

    var isQueueDisabled = isQueueActive.value == false

    fun getCantosListWithNumberAndName() = playListWithCantos.value?.cantos?.map {  it.getNumberWithName() }
}
