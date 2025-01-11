package com.example.singandsongs.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.singandsongs.data.canto.CantoRepository
import com.example.singandsongs.model.playlist.Kind
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel  @Inject constructor(
    private val cantoRepository: CantoRepository
) : ViewModel() {

    private val _categories = MutableLiveData<List<Kind>>().apply {
        value = Kind.values().asList()
    }

    val categories: LiveData<List<Kind>> = _categories
}


