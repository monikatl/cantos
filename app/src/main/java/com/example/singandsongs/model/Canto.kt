package com.example.singandsongs.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "canto_table")
data class Canto(
    val number: Int,
    val name: String,
    val kind: Kind,
    @PrimaryKey(autoGenerate = true)
    val cantoId: Long = 0
    )