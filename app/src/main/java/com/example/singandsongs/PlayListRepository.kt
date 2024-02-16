package com.example.singandsongs

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.singandsongs.model.PlayList
import java.time.LocalDate

object PlayListRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    val playLists: MutableList<PlayList> = mutableListOf(
        PlayList(
            1,
            LocalDate.now(),
            "Testowa",
            true,
            listOf(CantoRepository.list[0], CantoRepository.list[1], CantoRepository.list[2]))
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun add(playlist: PlayList) {
        playLists += playlist
    }
}