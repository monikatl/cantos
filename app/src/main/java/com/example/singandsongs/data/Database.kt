package com.example.singandsongs.data

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.singandsongs.converters.DateConverters
import com.example.singandsongs.data.canto.CantoDao
import com.example.singandsongs.data.playlist.PlayListDao
import com.example.singandsongs.model.playlist.Canto
import com.example.singandsongs.model.playlist.CantoPlayListCrossRef
import com.example.singandsongs.model.playlist.Content
import com.example.singandsongs.model.playlist.PlayList


@androidx.room.Database(entities = [Canto::class, PlayList::class, CantoPlayListCrossRef::class, Content::class], version = 32, exportSchema = false)
@TypeConverters(DateConverters::class)
abstract class Database : RoomDatabase() {
    abstract fun cantoDao(): CantoDao
    abstract fun playListDao(): PlayListDao


}
