package com.example.singandsongs.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class PlayListsWithCantos (
    @Embedded val playList: PlayList,
    @Relation(
        parentColumn = "playListId",
        entityColumn = "cantoId",
        associateBy = Junction(CantoPlayListCrossRef::class)
    )
    val cantos: List<Canto>
)