package com.mkabore.yt.data.api
/**
 * Created by @author mkabore
 * 2020-10-25
 */

import android.content.Context
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential
import com.google.api.client.http.HttpTransport
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.JsonFactory
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.client.util.ExponentialBackOff
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.YouTubeScopes
import com.google.common.collect.Lists
import com.mkabore.yt.data.db.entity.PlaylistItem
import com.mkabore.yt.data.db.entity.Video
import com.mkabore.yt.util.AppConstants.YOUTUBE_API_KEY
import java.lang.Exception

object YtApiService : YtApiHelper
{
    private var youtube: YouTube? = null

    private val HTTP_TRANSPORT: HttpTransport = NetHttpTransport()
    private val JSON_FACTORY: JsonFactory = JacksonFactory()


    private fun createGoogleAccountCredential(context: Context, userEmail:String) :GoogleAccountCredential
    {
          return GoogleAccountCredential.usingOAuth2(context, Lists.newArrayList(
            YouTubeScopes.YOUTUBE))
            .setSelectedAccountName(userEmail)
            .setBackOff(ExponentialBackOff())
    }

    override suspend fun getPlaylistItems(context: Context, userEmail:String?): List<PlaylistItem> {

        val dataToInsertInDB = mutableListOf<PlaylistItem>()

        try {

            var googleAccountCredential = createGoogleAccountCredential(context,userEmail?:"")

            // This object is used to make YouTube Data API requests.
            youtube = YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleAccountCredential)
                .setApplicationName("MK Youtube")
                .build()

            val playListQuery = youtube!!.playlists().list("snippet").setMine(true)
                .setKey(YOUTUBE_API_KEY)
                .setMaxResults(25)

            val playlistListResponse = playListQuery.execute()
            val playlistList = playlistListResponse.items
            if (playlistList.isEmpty()) {

                return dataToInsertInDB
            }

            var id = 0
            for(playlist in playlistList)
            {
                if(playlist != null)
                {
                    val playlistItem = PlaylistItem(
                        id++,
                        playlist.id,
                        playlist.etag,
                        playlist.snippet.thumbnails.high.url,
                        playlist.snippet.title,
                        playlist.snippet.channelTitle,
                        playlist.snippet.count()
                    )

                    dataToInsertInDB.add(playlistItem)
                }
            }
        }
        catch (exception : Exception)
        {
           println(exception.localizedMessage)
        }

        return dataToInsertInDB
    }

    override suspend fun getVideos(context: Context, userEmail:String?, author: String?, list_id: String?): List<Video> {
        val dataToInsertInDB = mutableListOf<Video>()

        try {

            var googleAccountCredential = createGoogleAccountCredential(context,userEmail?:"")

            // This object is used to make YouTube Data API requests.
            youtube = YouTube.Builder(HTTP_TRANSPORT, JSON_FACTORY, googleAccountCredential)
                .setApplicationName("MK Youtube")
                .build()

            val playListQuery = youtube!!.PlaylistItems().list("snippet,contentDetails,status").setPlaylistId(list_id).setMaxResults(25)
                .setKey(YOUTUBE_API_KEY)

            val playlistListResponse = playListQuery.execute()
            val videoList = playlistListResponse.items
            if (videoList.isEmpty()) {

                return dataToInsertInDB
            }

            var id = 0
            for(aVideo in videoList)
            {
                if(aVideo != null)
                {
                    val contentDetail = aVideo.contentDetails
                    val video = Video(
                        id++,
                        aVideo.snippet.title,
                        aVideo.snippet.thumbnails.high.url,
                        author,
                        contentDetail["duration"].toString()
                    )

                    dataToInsertInDB.add(video)
                }
            }
        }
        catch (exception : Exception)
        {
            println(exception.localizedMessage)
        }

        return dataToInsertInDB
    }

}