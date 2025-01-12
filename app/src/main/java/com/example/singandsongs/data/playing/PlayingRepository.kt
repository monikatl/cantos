package com.example.singandsongs.data.playing

import androidx.annotation.WorkerThread
import com.example.singandsongs.model.playing.Playing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PlayingRepository @Inject constructor(
  private val playingDao: PlayingDao
) {
  fun getAllPlayings() = playingDao.getAllPlayings()

  @WorkerThread
  suspend fun insertPlaying(playing: Playing) = withContext(Dispatchers.IO) {
    playingDao.insertPlaying(playing)
  }

  @WorkerThread
  suspend fun deletePlaying(playing: Playing) = withContext(Dispatchers.IO) {
    playingDao.deletePlaying(playing)
  }
}
