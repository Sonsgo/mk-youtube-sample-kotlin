package com.mkabore.yt.ui.dashboard

/**
 * Created by @author mkabore
 * 2020-10-25
 */

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkabore.yt.data.api.YtApiService
import com.mkabore.yt.data.db.DatabaseHelper
import com.mkabore.yt.data.db.entity.Video
import com.mkabore.yt.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class VideoListViewModel(private val context: Context, private val apiService: YtApiService, private val dbHelper: DatabaseHelper,
                         private val author:String, private val list_id: String) : ViewModel() {

    private val videoList = MutableLiveData<Resource<List<Video>>>()

    private val ioScope = CoroutineScope(Dispatchers.IO + Job() )

    init {
        fetchUserVideoList()
    }


    private fun fetchUserVideoList()
    {
        ioScope.launch {
            videoList.postValue(Resource.loading(null))
            try {
               val user = dbHelper.getUsers()[0]
                val videoItemsFromApi = apiService.getVideos(context, user.email, author, list_id)
                videoList.postValue(Resource.success(videoItemsFromApi))

            } catch (e: Exception) {
                videoList.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }


//
//    private fun fetchUserVideoList()
//    {
//        ioScope.launch {
//            videoList.postValue(Resource.loading(null))
//            try {
//                val user = dbHelper.getUsers()[0]
//                val videoItemsFromDb = dbHelper.getVideos()
//                if (videoItemsFromDb.isEmpty()) {
//                    val videoItemsFromApi = apiService.getVideos(context, user.email, author, list_id)
//                    dbHelper.insertAllVideos(videoItemsFromApi)
//
//                    videoList.postValue(Resource.success(videoItemsFromApi))
//
//                } else {
//                    videoList.postValue(Resource.success(videoItemsFromDb))
//                }
//
//            } catch (e: Exception) {
//                videoList.postValue(Resource.error("Something Went Wrong", null))
//            }
//        }
//    }

    fun getVideolist(): LiveData<Resource<List<Video>>> {
        return videoList
    }
}