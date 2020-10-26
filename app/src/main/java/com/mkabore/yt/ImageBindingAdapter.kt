package com.mkabore.yt

/**
 * Created by @author mkabore
 * 2020-10-25
 */

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

object ImageBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:src")
    fun setImageUrl(view: ImageView, url: String) {
        if(view.id == R.id.user_avatar)
        {
            if(url.isEmpty())
            {
                view.setBackgroundResource(android.R.drawable.ic_menu_camera);
                return
            }
            Glide.with(view.context).load(url).apply( RequestOptions().circleCrop()).into(view)
        }
        else
        {
            Glide.with(view.context).load(url).into(view)
        }

    }
}