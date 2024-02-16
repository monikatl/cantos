package com.example.singandsongs.data

import androidx.room.*
import com.example.singandsongs.model.CantoPlayListCrossRef
import com.example.singandsongs.model.PlayListsWithCantos
import com.example.singandsongs.model.PlayList
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayListDao {

    @Transaction
    @Query("SELECT * FROM play_list_table")
    fun getPlayList(): Flow<List<PlayList>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayList(playList: PlayList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCantoPlayListCrossRef(ref: CantoPlayListCrossRef)

    @Delete
    fun deletePlayList(playList: PlayList)

    @Delete
    fun deleteCantoPlayListCrossRef(ref: CantoPlayListCrossRef)

}