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
import com.mkabore.yt.data.db.entity.PlaylistItem
import com.mkabore.yt.util.PlaylistItemClickListener
import kotlinx.android.synthetic.main.playlist_item_layout.view.*

class PlaylistItemAdapter(
    private val playlistItems: ArrayList<PlaylistItem>, private val clickListener: PlaylistItemClickListener
) : RecyclerView.Adapter<PlaylistItemAdapter.DataViewHolder>() {

    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(playlistItem: PlaylistItem) {
            itemView.playlist_item_title.text = playlistItem.title
            itemView.playlist_item_author.text = "Playlist - "  .plus(playlistItem.channelTitle)
            itemView.playlist_item_tracks_count.text = playlistItem.tracksCount.toString().plus(" Songs")
            Glide.with(itemView.playlist_item_thumb.context)
                .load(playlistItem.thumbnailUrl)
                .into(itemView.playlist_item_thumb)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.playlist_item_layout, parent,
                false
            )
        )

    override fun getItemCount(): Int = playlistItems.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        val playlistItem = playlistItems[position]
        holder.bind(playlistItem)
        holder.itemView.setOnClickListener() {
            clickListener.onClickListener(playlistItem)
        }
    }


    fun addData(list: List<PlaylistItem>) {
        playlistItems.addAll(list)
    }

}