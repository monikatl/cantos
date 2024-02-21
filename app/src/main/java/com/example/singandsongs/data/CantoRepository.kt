package com.example.singandsongs.data

import androidx.annotation.WorkerThread
import com.example.singandsongs.model.Canto
import com.example.singandsongs.model.Kind
import com.example.singandsongs.utils.FilterCondition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CantoRepository @Inject constructor(
    private val cantoDao: CantoDao
) {
    fun getAllCantos(condition: FilterCondition) = when(condition) {
        FilterCondition.CANTOS -> cantoDao.getAllCantos()
        FilterCondition.DRAFTS -> cantoDao.getAllDrafts()
        FilterCondition.FAVOURITE -> cantoDao.getAllFavourites()
    }

    fun getCantosByCategory(category: Kind) = cantoDao.getAllCantosByKind(category)

    @WorkerThread
    suspend fun insertCanto(canto: Canto) = withContext(Dispatchers.IO) {
        cantoDao.insertCanto(canto)
    }

    @WorkerThread
    suspend fun updateCanto(canto: Canto) = withContext(Dispatchers.IO) {
        cantoDao.updateCanto(canto)
    }

    @WorkerThread
    suspend fun deleteCanto(canto: Canto) = withContext(Dispatchers.IO) {
        cantoDao.deleteCanto(canto)
    }

}