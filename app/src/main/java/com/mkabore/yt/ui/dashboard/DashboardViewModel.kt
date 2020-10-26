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
import com.mkabore.yt.data.db.entity.PlaylistItem
import com.mkabore.yt.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DashboardViewModel(private val context: Context, private val apiService: YtApiService, private val dbHelper: DatabaseHelper) : ViewModel() {

    private val playlistItems = MutableLiveData<Resource<List<PlaylistItem>>>()

    private val ioScope = CoroutineScope(Dispatchers.IO + Job() )

    init {
        fetchUserPlayLists()
    }


    private fun fetchUserPlayLists()
    {
        ioScope.launch {
            playlistItems.postValue(Resource.loading(null))
            try {
                val user = dbHelper.getUsers()[0]
                val playlistItemsFromDb = dbHelper.getPlaylistItems()
                if (playlistItemsFromDb.isEmpty()) {
                    val playlistItemsFromApi = apiService.getPlaylistItems(context, user.email)
                     dbHelper.insertAllPlaylistItems(playlistItemsFromApi)

                    playlistItems.postValue(Resource.success(playlistItemsFromApi))

                } else {
                    playlistItems.postValue(Resource.success(playlistItemsFromDb))
                }

            } catch (e: Exception) {
                playlistItems.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    fun getPlaylists(): LiveData<Resource<List<PlaylistItem>>> {
        return playlistItems
    }
}