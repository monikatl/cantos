package com.example.singandsongs.model.playing

import com.example.singandsongs.model.playlist.PlayList
import java.time.LocalDate

data class FullPlaying(val data: LocalDate, val name: String, val place: Place?, val time: String, val playList: PlayList?, val playing: Playing)
