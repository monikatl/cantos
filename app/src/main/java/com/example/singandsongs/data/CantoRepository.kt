package com.example.singandsongs.data

import androidx.annotation.WorkerThread
import com.example.singandsongs.model.Canto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CantoRepository @Inject constructor(
    private val cantoDao: CantoDao
) {

    val getAllCantos: Flow<List<Canto>> = cantoDao.getAllCantos()

    @WorkerThread
    suspend fun insertCanto(canto: Canto) = withContext(Dispatchers.IO) {
        cantoDao.insertCanto(canto)
    }

    @WorkerThread
    suspend fun deleteCanto(canto: Canto) = withContext(Dispatchers.IO) {
        cantoDao.deleteCanto(canto)
    }
}