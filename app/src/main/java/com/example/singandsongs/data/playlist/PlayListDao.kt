package com.example.singandsongs.data.playlist

import androidx.room.*
import com.example.singandsongs.model.playlist.CantoPlayListCrossRef
import com.example.singandsongs.model.playlist.PlayList
import com.example.singandsongs.model.playlist.PlayListWithCantos
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayListDao {

    @Transaction
    @Query("SELECT * FROM play_list_table")
    fun getPlayList(): Flow<List<PlayList>>

    @Transaction
    @Query("SELECT * FROM play_list_table WHERE is_current = true LIMIT 1")
    fun getPlayListWithCantos(): Flow<PlayListWithCantos>

    @Transaction
    @Query("SELECT * FROM play_list_table WHERE playListId = :id LIMIT 1")
    fun getPlayListWithCantosById(id: Long): Flow<PlayListWithCantos>

    @Query("SELECT * FROM play_list_table WHERE is_current = true LIMIT 1")
    fun getCurrentPlayList(): Flow<PlayList>

    @Query("SELECT EXISTS(SELECT 1 FROM play_list_table WHERE is_current = true LIMIT 1)")
    fun isAttached(): Flow<Boolean>

    @Query("SELECT * FROM canto_with_playlist_table")
    fun getRefList(): Flow<List<CantoPlayListCrossRef>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPlayList(playList: PlayList)

    @Update
    fun updatePlayList(playList: PlayList)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCantoPlayListCrossRef(ref: CantoPlayListCrossRef)

    @Delete
    fun deletePlayList(playList: PlayList)

    @Delete
    fun deleteCantoPlayListCrossRef(ref: CantoPlayListCrossRef)

}
