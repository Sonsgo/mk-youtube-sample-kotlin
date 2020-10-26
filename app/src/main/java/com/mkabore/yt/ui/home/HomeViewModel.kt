package com.mkabore.yt.ui.home

/**
 * Created by @author mkabore
 * 2020-10-25
 */

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mkabore.yt.data.db.entity.User

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "The last 5 played videos"
    }

    val text: LiveData<String> = _text

    // User Name
    private val _userName = MutableLiveData<String>()
    val userName: LiveData<String>
        get() = _userName


    // User Email
    private val _userEmail = MutableLiveData<String>()
    val userEmail: LiveData<String>
        get() = _userEmail

    // User Picture
    private val _userAvatarUrl = MutableLiveData<String>()
    val userAvatarUrl: LiveData<String>
        get() = _userAvatarUrl

    init {
        _userName.value = ""
        _userEmail.value = ""
        _userAvatarUrl.value = ""
    }

    fun updateUserData(user: User)
    {
        _userName.value = user.firstName + " " + user.lastName
        _userEmail.value = user.email
        _userAvatarUrl.value = user.avatarUrl
    }
}