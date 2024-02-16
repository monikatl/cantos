package com.example.singandsongs.data

import com.example.singandsongs.Cantos
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class DatabaseInit @Inject constructor(private val cantoDao: CantoDao) {

    fun initialize() {
            for (canto in Cantos.list) {
                cantoDao.insertCanto(canto)
            }
    }
}


