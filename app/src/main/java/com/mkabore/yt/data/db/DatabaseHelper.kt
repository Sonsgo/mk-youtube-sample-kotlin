package com.mkabore.yt.data.db
/**
 * Created by @author mkabore
 * 2020-10-25
 */
import com.mkabore.yt.data.db.entity.PlaylistItem
import com.mkabore.yt.data.db.entity.User
import com.mkabore.yt.data.db.entity.Video

interface DatabaseHelper {

    suspend fun getUsers(): List<User>

    suspend fun insertAllUsers(users: List<User>)


    suspend fun getPlaylistItems(): List<PlaylistItem>

    suspend fun insertAllPlaylistItems(playlistItems: List<PlaylistItem>)


    suspend fun getVideos(): List<Video>

    suspend fun insertAllVideos(videos: List<Video>)

}