package com.example.singandsongs.data

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.singandsongs.converters.DateConverters
import com.example.singandsongs.model.Canto
import com.example.singandsongs.model.CantoPlayListCrossRef
import com.example.singandsongs.model.Content
import com.example.singandsongs.model.PlayList


@androidx.room.Database(entities = [Canto::class, PlayList::class, CantoPlayListCrossRef::class, Content::class], version = 28, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class Database : RoomDatabase() {
    abstract fun cantoDao(): CantoDao
    abstract fun playListDao(): PlayListDao


}
