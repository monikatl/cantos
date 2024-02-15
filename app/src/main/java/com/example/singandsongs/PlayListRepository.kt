package com.example.singandsongs

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.singandsongs.model.PlayList
import java.time.LocalDate

object PlayListRepository {
    @RequiresApi(Build.VERSION_CODES.O)
    val playLists: List<PlayList> = listOf(
        PlayList(
            1,
            LocalDate.now(),
            "Testowa",
            listOf(CantoRepository.list[0], CantoRepository.list[1], CantoRepository.list[2]))
    )
}