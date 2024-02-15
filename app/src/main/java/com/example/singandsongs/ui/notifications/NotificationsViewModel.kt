package com.example.singandsongs.ui.notifications

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.singandsongs.CantoRepository
import com.example.singandsongs.PlayListRepository
import com.example.singandsongs.model.Canto
import com.example.singandsongs.model.PlayList

class NotificationsViewModel : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    private val _playLists = MutableLiveData<List<PlayList>>().apply {
        value = PlayListRepository.playLists
    }
    @RequiresApi(Build.VERSION_CODES.O)
    val playLists: LiveData<List<PlayList>> = _playLists
}