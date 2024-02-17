package com.example.singandsongs.ui.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.singandsongs.data.CantoRepository
import com.example.singandsongs.model.Canto
import com.example.singandsongs.model.Kind
import com.example.singandsongs.model.PlayList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val cantoRepository: CantoRepository
) : ViewModel() {

    val cantos: LiveData<List<Canto>> = cantoRepository.getAllCantos.asLiveData()

    val addCanto: (Int, String, Kind) -> Unit = { number, name, kind ->
        val canto = Canto(number, name, kind)
        viewModelScope.launch(Dispatchers.IO) {
            cantoRepository.insertCanto(canto)
        }
    }

}