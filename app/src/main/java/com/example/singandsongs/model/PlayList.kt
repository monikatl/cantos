package com.example.singandsongs.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity (tableName = "play_list_table")
data class PlayList(
    val data: LocalDate,
    val name: String,
    var isDefault: Boolean = true,
    @PrimaryKey(autoGenerate = true)
    val playListId: Long = 0
) {
}