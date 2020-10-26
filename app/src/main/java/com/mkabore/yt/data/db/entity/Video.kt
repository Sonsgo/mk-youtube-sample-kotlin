package com.mkabore.yt.data.db.entity
/**
 * Created by @author mkabore
 * 2020-10-25
 */
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Video(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String?,
    @ColumnInfo(name = "author") val author: String?,
    @ColumnInfo(name = "duration") val duration: String?
)