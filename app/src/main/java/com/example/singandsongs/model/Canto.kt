package com.example.singandsongs.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "canto_table")
data class Canto(
    @PrimaryKey(autoGenerate = true)
    val cantoId: Long,
    val name: String,
    val kind: Kind)