package com.example.singandsongs.model

import androidx.room.Entity

@Entity(tableName = "canto_with_playlist_table", primaryKeys=["cantoId", "playListId"])
data class CantoPlayListCrossRef(
    val cantoId: Long,
    val playListId: Long
)