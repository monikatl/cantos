package com.example.singandsongs.model

import java.time.LocalDate

data class PlayList(val id: Int, val data: LocalDate, val name: String, val cantos: List<Canto>) {
}