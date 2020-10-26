package com.mkabore.yt.data.db.dao
/**
 * Created by @author mkabore
 * 2020-10-25
 */

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mkabore.yt.data.db.entity.PlaylistItem

@Dao
interface PlaylistItemDao {

    @Query("SELECT * FROM playlistItem")

    suspend fun getAll(): List<PlaylistItem>

    @Insert
    suspend fun insertAll(playlistItem: List<PlaylistItem>)

    @Delete
    suspend fun delete(playlistItem: PlaylistItem)

}