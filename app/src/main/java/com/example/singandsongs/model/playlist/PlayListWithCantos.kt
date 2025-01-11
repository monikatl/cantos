package com.example.singandsongs.model.playlist

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation


data class PlayListWithCantos (
  @Embedded val playList: PlayList,
  @Relation(
        parentColumn = "playListId",
        entityColumn = "cantoId",
        associateBy = Junction(CantoPlayListCrossRef::class)
    )
    val cantos: List<Canto>
){
    fun convertToSend(): String {
        val listToSend = StringBuilder()
        listToSend.append(playList.name, "\n")
        cantos.forEach { listToSend.append(it.number, " ", it.name, " ", it.currentSheetCount, "\n") }
        return listToSend.toString()
    }
}
