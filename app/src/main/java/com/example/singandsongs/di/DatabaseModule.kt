package com.example.singandsongs.di

import android.content.Context
import androidx.room.Room
import com.example.singandsongs.data.*
import com.example.singandsongs.data.canto.CantoDao
import com.example.singandsongs.data.canto.CantoRepository
import com.example.singandsongs.data.playlist.PlayListDao
import com.example.singandsongs.data.playlist.PlayListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun providesDB(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(context,Database::class.java,"CantoDatabase.db").fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providesCantoDao(database: Database): CantoDao = database.cantoDao()

    @Provides
    fun providesCantoRepository(cantoDao: CantoDao) : CantoRepository
            = CantoRepository(cantoDao)

    @Provides
    fun providesPlayListDao(database: Database): PlayListDao = database.playListDao()

    @Provides
    fun providesPlayListRepository(playListDao: PlayListDao) : PlayListRepository
            = PlayListRepository(playListDao)

}
