package com.example.singandsongs.model.playlist


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "content_table")
data class Content(
    val cantoId: Long?,
    var text: String,
    @PrimaryKey
    val contentId: Long? = cantoId
)
