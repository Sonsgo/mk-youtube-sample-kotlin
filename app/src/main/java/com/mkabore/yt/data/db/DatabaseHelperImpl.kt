package com.mkabore.yt.data.db
/**
 * Created by @author mkabore
 * 2020-10-25
 */
import com.mkabore.yt.data.db.entity.PlaylistItem
import com.mkabore.yt.data.db.entity.User
import com.mkabore.yt.data.db.entity.Video

class DatabaseHelperImpl(private val appDatabase: AppDatabase) : DatabaseHelper {

    override suspend fun getUsers(): List<User> = appDatabase.userDao().getAll()

    override suspend fun insertAllUsers(users: List<User>) = appDatabase.userDao().insertAll(users)


    override suspend fun getVideos(): List<Video> = appDatabase.videoDao().getAll()

    override suspend fun insertAllVideos(videos: List<Video>) = appDatabase.videoDao().insertAll(videos)


    override suspend fun getPlaylistItems(): List<PlaylistItem> = appDatabase.playlistItemDao().getAll()

    override suspend fun insertAllPlaylistItems(playlistItems: List<PlaylistItem>) = appDatabase.playlistItemDao().insertAll(playlistItems)
}