package com.mkabore.yt.data.db.dao
/**
 * Created by @author mkabore
 * 2020-10-25
 */

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mkabore.yt.data.db.entity.Video

@Dao
interface VideoDao {

    @Query("SELECT * FROM video")
    suspend fun getAll(): List<Video>

    @Insert
    suspend fun insertAll(videos: List<Video>)

    @Delete
    suspend fun delete(video: Video)

}