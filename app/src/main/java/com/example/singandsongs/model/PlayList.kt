package com.example.singandsongs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity (tableName = "play_list_table")
data class PlayList(
    val data: LocalDate,
    val name: String,

    @ColumnInfo(name = "is_current")
    var isCurrent: Boolean = true,
    @ColumnInfo(name = "frequency_counter")
    var frequencyCounter: Long = 0L,
    @PrimaryKey(autoGenerate = true)
    val playListId: Long = 0
) {

    fun setCurrent() {
        isCurrent = true
        frequencyCounter++
    }

    fun disconnectPlayList(): PlayList{
        isCurrent = false
        return this
    }
}