package com.mkabore.yt.data.api

/**
 * Created by @author mkabore
 * 2020-10-25
 */
import android.content.Context
import com.mkabore.yt.data.db.entity.PlaylistItem
import com.mkabore.yt.data.db.entity.Video

interface YtApiHelper {

    suspend fun getPlaylistItems(context: Context, userEmail:String?): List<PlaylistItem>

    suspend fun getVideos(context: Context, userEmail:String?, author:String?, list_id: String?): List<Video>

}