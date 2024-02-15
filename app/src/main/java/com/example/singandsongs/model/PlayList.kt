package com.example.singandsongs.model

import java.time.LocalDate

data class PlayList(val data: LocalDate, val name: String, val cantos: List<Canto>) {
}