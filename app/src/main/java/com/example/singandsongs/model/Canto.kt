package com.example.singandsongs.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "canto_table")
data class Canto(
    val number: Int,
    val name: String,
    val kind: Kind,
    @ColumnInfo(name = "is_favourite")
    var isFavourite: Boolean = false,
    @PrimaryKey(autoGenerate = true)
    val cantoId: Long = 0
    ) {
    fun checkAsFavourite() {
        isFavourite = !isFavourite
    }

    fun uncheckAsFavourite() {
        isFavourite = !isFavourite
    }
}