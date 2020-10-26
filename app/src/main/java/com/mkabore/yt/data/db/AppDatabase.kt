package com.mkabore.yt.data.db
/**
 * Created by @author mkabore
 * 2020-10-25
 */
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mkabore.yt.data.db.dao.PlaylistItemDao
import com.mkabore.yt.data.db.dao.UserDao
import com.mkabore.yt.data.db.dao.VideoDao
import com.mkabore.yt.data.db.entity.PlaylistItem
import com.mkabore.yt.data.db.entity.User
import com.mkabore.yt.data.db.entity.Video


@Database(entities = [User::class, PlaylistItem::class, Video::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao

    abstract fun playlistItemDao() : PlaylistItemDao

    abstract fun videoDao(): VideoDao
}