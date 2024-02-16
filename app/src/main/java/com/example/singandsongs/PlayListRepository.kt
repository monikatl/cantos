package com.example.singandsongs

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.singandsongs.model.PlayList
import java.time.LocalDate

object PlayListRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    val playLists: MutableList<PlayList> = mutableListOf(
        PlayList(
            LocalDate.now(),
            "Testowa",
            true
        )
    )

    @RequiresApi(Build.VERSION_CODES.O)
    fun add(playlist: PlayList) {
        playLists += playlist
    }
}