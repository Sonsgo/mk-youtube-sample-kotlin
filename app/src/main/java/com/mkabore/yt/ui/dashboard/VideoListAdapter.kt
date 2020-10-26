package com.mkabore.yt.ui.dashboard

/**
 * Created by @author mkabore
 * 2020-10-25
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkabore.yt.R
import com.mkabore.yt.data.db.entity.Video
import com.mkabore.yt.util.PlaylistItemClickListener
import kotlinx.android.synthetic.main.video_list_item_layout.view.*

class VideoListAdapter(
    private val videoList: ArrayList<Video>, private val clickListener: PlaylistItemClickListener
) : RecyclerView.Adapter<VideoListAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(video: Video) {
            itemView.video_title.text = video.title
            itemView.video_author.text = video.author
            Glide.with(itemView.video_thumb.context)
                .load(video.thumbnailUrl)
                .into(itemView.video_thumb)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.video_list_item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = videoList.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(videoList[position])
    }


    fun addData(list: List<Video>) {
        videoList.addAll(list)
    }

}