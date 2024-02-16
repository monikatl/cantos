package com.example.singandsongs.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.singandsongs.data.CantoRepository
import com.example.singandsongs.model.Canto
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cantoRepository: CantoRepository
) : ViewModel() {

    val cantos: LiveData<List<Canto>> = cantoRepository.getAllCantos.asLiveData()

}