package com.example.singandsongs.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.singandsongs.data.canto.CantoRepository
import com.example.singandsongs.model.playlist.Canto
import com.example.singandsongs.model.playlist.Kind
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val cantoRepository: CantoRepository
) : ViewModel() {


    lateinit var cantos: LiveData<List<Canto>>

    lateinit var category: Kind

    fun initList(category: Kind) {
        cantos = cantoRepository.getCantosByCategory(category).asLiveData()
        this.category = category
    }

}
