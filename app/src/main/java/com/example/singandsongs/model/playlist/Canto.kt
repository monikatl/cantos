package com.example.singandsongs.model.playlist

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "canto_table")
data class Canto(
  var number: Int?,
  var name: String,
  var kind: Kind? = Kind.ACCIDENTAL,
  var sheetCounter: Int? = 0,
  var sheets: String = "",
  var fileName: String = "",
  @ColumnInfo(name = "is_favourite")
    var isFavourite: Boolean = false,
  @ColumnInfo(name = "is_draft")
    var isDraft: Boolean = false,
  @PrimaryKey(autoGenerate = true)
    val cantoId: Long = 0
    ) {
  var currentSheetCount: Int? = sheetCounter

  fun checkAsFavourite() {
        isFavourite = !isFavourite
    }

    fun transformDraft() {
        isDraft = !isDraft
    }

    companion object {
        fun createDraftCanto(name: String, number: Int? = null, kind: Kind? = null, pageCounter: Int? = null): Canto {
            return Canto(number, name, kind, pageCounter, isDraft = true)
        }

    }
}
