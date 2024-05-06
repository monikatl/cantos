package com.example.singandsongs.data

import androidx.annotation.WorkerThread
import com.example.singandsongs.model.CantoPlayListCrossRef
import com.example.singandsongs.model.PlayList
import com.example.singandsongs.model.PlayListWithCantos
import com.example.singandsongs.utils.SortCondition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayListRepository @Inject constructor(
    private val playListDao: PlayListDao
) {
    fun getAllPlayLists(condition: SortCondition): Flow<List<PlayList>> {
        return playListDao.getPlayList().map { pl ->
            when(condition) {
                SortCondition.AZ -> pl.sortedBy { it.name }
                SortCondition.ZA -> pl.sortedBy { it.name }.reversed()
                SortCondition.FREQ_ASC -> pl.sortedBy { it.frequencyCounter }
                SortCondition.FREQ_DESC -> pl.sortedBy { it.frequencyCounter }.reversed()
                SortCondition.DATE_ASC -> pl.sortedBy { it.data }
                SortCondition.DATE_DESC -> pl.sortedBy { it.data }.reversed()
                else -> pl.sortedBy { it.playListId }
            }
        }
    }

    val getCurrentPlayList: Flow<PlayList> = playListDao.getCurrentPlayList()

    val isAttached: Flow<Boolean> = playListDao.isAttached()

    val getPlayListWithCantos: Flow<PlayListWithCantos> = playListDao.getPlayListWithCantos()

    fun getPlayListWithCantosById(id: Long): Flow<PlayListWithCantos> = playListDao.getPlayListWithCantosById(id)

    @WorkerThread
    suspend fun insertPlayList(playList: PlayList) = withContext(Dispatchers.IO) {
        playListDao.insertPlayList(playList)
    }

    @WorkerThread
    suspend fun editPlayList(playList: PlayList) = withContext(Dispatchers.IO) {
        playListDao.updatePlayList(playList)
    }

    @WorkerThread
    suspend fun deletePlaylist(playList: PlayList) = withContext(Dispatchers.IO) {
        playListDao.deletePlayList(playList)
    }

    @WorkerThread
    suspend fun insertCantoPlayListCrossRef(ref: CantoPlayListCrossRef) = withContext(Dispatchers.IO) {
        playListDao.insertCantoPlayListCrossRef(ref)
    }



    @WorkerThread
    suspend fun deleteCantoPlayListCrossRef(ref: CantoPlayListCrossRef) = withContext(Dispatchers.IO) {
        playListDao.deleteCantoPlayListCrossRef(ref)
    }

}
