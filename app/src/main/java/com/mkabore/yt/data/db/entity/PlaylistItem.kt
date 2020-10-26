package com.mkabore.yt.data.db.entity
/**
 * Created by @author mkabore
 * 2020-10-25
 */
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PlaylistItem(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "list_id") val list_id: String?,
    @ColumnInfo(name = "e_tag") val eTag: String?,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "channel_title") val channelTitle: String?,
    @ColumnInfo(name = "tracks_count") val tracksCount: Int?
)