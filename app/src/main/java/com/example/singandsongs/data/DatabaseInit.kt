package com.example.singandsongs.data

import android.content.Context
import com.example.singandsongs.Cantos
import javax.inject.Inject

class DatabaseInit @Inject constructor(private val cantoDao: CantoDao) {

    fun initialize() {
            for (canto in Cantos.list) {
                cantoDao.insertCanto(canto)
            }
    }
}


