package com.mkabore.yt.util

import com.mkabore.yt.data.db.entity.PlaylistItem


/**
 * Created by @author mkabore
 * 2020-10-25
 */

interface PlaylistItemClickListener {
    fun onClickListener(playlistItem: PlaylistItem)
}