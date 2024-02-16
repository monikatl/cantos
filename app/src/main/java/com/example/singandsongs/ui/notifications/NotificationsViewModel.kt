package com.example.singandsongs.ui.notifications

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.singandsongs.data.PlayListRepository
import com.example.singandsongs.model.PlayList
import com.example.singandsongs.model.PlayListsWithCantos
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val cantoRepository: PlayListRepository
) : ViewModel() {


    @RequiresApi(Build.VERSION_CODES.O)
    val playLists: LiveData<List<PlayList>> = cantoRepository.getAllPlayLists.asLiveData()


    @RequiresApi(Build.VERSION_CODES.O)
    val addNewPlayList: (String, Boolean) -> Unit = { name, isDefault ->
        val playList = PlayList(LocalDate.now(), name, isDefault)
        viewModelScope.launch(Dispatchers.IO) {
            cantoRepository.insertPlayList(playList)
        }
    }
}