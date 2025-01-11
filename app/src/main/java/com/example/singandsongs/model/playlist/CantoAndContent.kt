package com.example.singandsongs.model.playlist

import androidx.room.Embedded
import androidx.room.Relation

data class CantoAndContent (
    @Embedded
    val canto: Canto,
    @Relation(
        parentColumn = "cantoId",
        entityColumn = "contentId"
    )
    val content: Content?
)
