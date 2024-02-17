package com.example.singandsongs.data

import androidx.annotation.WorkerThread
import com.example.singandsongs.model.CantoPlayListCrossRef
import com.example.singandsongs.model.PlayList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayListRepository@Inject constructor(
    private val playListDao: PlayListDao
) {

    val getAllPlayLists: Flow<List<PlayList>> = playListDao.getPlayList()

    val getCurrentPlayList: Flow<PlayList> = playListDao.getCurrentPlayList()

    val isAttached: Flow<Boolean> = playListDao.isAttached()

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