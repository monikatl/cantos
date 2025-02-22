package com.example.singandsongs.data

import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.singandsongs.converters.DateConverters
import com.example.singandsongs.converters.ListConverters
import com.example.singandsongs.converters.LongListConverters
import com.example.singandsongs.data.canto.CantoDao
import com.example.singandsongs.data.day.DayDao
import com.example.singandsongs.data.place.PlaceDao
import com.example.singandsongs.data.playing.PlayingDao
import com.example.singandsongs.data.playlist.PlayListDao
import com.example.singandsongs.model.playing.Day
import com.example.singandsongs.model.playing.Place
import com.example.singandsongs.model.playing.Playing
import com.example.singandsongs.model.playlist.Canto
import com.example.singandsongs.model.playlist.CantoPlayListCrossRef
import com.example.singandsongs.model.playlist.Content
import com.example.singandsongs.model.playlist.PlayList


@androidx.room.Database(entities = [Canto::class, PlayList::class, CantoPlayListCrossRef::class, Content::class, Place::class, Playing::class, Day::class], version = 46, exportSchema = false)
@TypeConverters(DateConverters::class, ListConverters::class, LongListConverters::class)
abstract class Database : RoomDatabase() {
    abstract fun cantoDao(): CantoDao
    abstract fun playListDao(): PlayListDao
    abstract fun placeDao(): PlaceDao
    abstract fun playingDao(): PlayingDao
    abstract fun dayDao(): DayDao
}
