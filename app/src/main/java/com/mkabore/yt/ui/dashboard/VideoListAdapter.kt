package com.mkabore.yt.ui.dashboard

/**
 * Created by @author mkabore
 * 2020-10-25
 */

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkabore.yt.R
import com.mkabore.yt.data.db.entity.Video
import com.mkabore.yt.util.PlaylistItemClickListener
import kotlinx.android.synthetic.main.video_list_item_layout.view.*
import java.util.*
import kotlin.collections.ArrayList

class VideoListAdapter(
    private val videoList: ArrayList<Video>, private val clickListener: PlaylistItemClickListener
) : RecyclerView.Adapter<VideoListAdapter.DataViewHolder>(), Filterable {

    var videoFilterList = ArrayList<Video>()

    fun setNonFilteredVideoList(videoList : ArrayList<Video>)
    {
        videoFilterList = videoList
    }


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


    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                var listToReturn =  ArrayList<Video>()
                if(charSearch.isEmpty()) {
                    listToReturn = videoFilterList
                } else {
                    val resultList = ArrayList<Video>()
                    for (row in videoFilterList) {
                        if (row.title.toString().toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
                            resultList.add(row)
                        }
                    }
                    listToReturn.addAll(resultList)
                }
                val filterResults = FilterResults()
                filterResults.values = listToReturn
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                var videoFilterList = results?.values as ArrayList<Video>
                renderFilteredList(videoFilterList)
            }
        }
    }

    private fun renderFilteredList(videoFilterList: List<Video>) {
        if(videoFilterList.isNotEmpty())
        {
            videoList.clear()
            addData(videoFilterList)
            notifyDataSetChanged()
        }
    }
}