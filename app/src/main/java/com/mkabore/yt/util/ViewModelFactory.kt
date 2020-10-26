package com.mkabore.yt.util

/**
 * Created by @author mkabore
 * 2020-10-25
 */

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mkabore.yt.data.api.YtApiService
import com.mkabore.yt.data.db.DatabaseHelper
import com.mkabore.yt.ui.dashboard.DashboardViewModel
import com.mkabore.yt.ui.dashboard.VideoListViewModel


class ViewModelFactory(private val context: Context, private val apiService: YtApiService, private val dbHelper: DatabaseHelper,
        private val author:String, private val list_id : String) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(context,apiService, dbHelper) as T
        }

        if (modelClass.isAssignableFrom(VideoListViewModel::class.java)) {
            return VideoListViewModel(context,apiService, dbHelper,author, list_id) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}