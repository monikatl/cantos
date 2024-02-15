package com.example.singandsongs.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.singandsongs.CantoRepository
import com.example.singandsongs.model.Canto

class HomeViewModel : ViewModel() {

    private val _cantos = MutableLiveData<List<Canto>>().apply {
        value = CantoRepository.list
    }
    val cantos: LiveData<List<Canto>> = _cantos
}