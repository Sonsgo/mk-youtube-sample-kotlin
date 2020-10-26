package com.mkabore.yt.ui.dashboard

/**
 * Created by @author mkabore
 * 2020-10-25
 */
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mkabore.yt.R
import com.mkabore.yt.data.api.YtApiService
import com.mkabore.yt.data.db.DatabaseBuilder
import com.mkabore.yt.data.db.DatabaseHelperImpl
import com.mkabore.yt.data.db.entity.PlaylistItem
import com.mkabore.yt.data.db.entity.Video
import com.mkabore.yt.databinding.FragmentPlaylistBinding
import com.mkabore.yt.util.PlaylistItemClickListener
import com.mkabore.yt.util.ResultStatus
import com.mkabore.yt.util.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_dashboard.*

class PlayerListFragment : Fragment(), PlaylistItemClickListener
{
    private lateinit var binding: FragmentPlaylistBinding

    private lateinit var videoListViewModel: VideoListViewModel
    private lateinit var adapter: VideoListAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_playlist,
            container,
            false
        )

        val rootView = binding.root;



        recyclerView = rootView.findViewById(R.id.recyclerView)

        setupHeader(rootView)
        setupUI(rootView.context)
        setupViewModel(rootView.context)
        setupObserver(rootView.context)

        setupSearchView(rootView)

        return rootView
    }

    private fun setupHeader(rootView: View)
    {
        val author  = arguments?.getString("author")
        val channelTitle = arguments?.getString("channel_title")
        val thumbnail = arguments?.getString("thumbnail_url")
        val tracksCount = arguments?.getInt("tracks_count")

        val titleView = rootView.findViewById<TextView>(R.id.playlist_item_detail_title)
        titleView.text = author

        val a = rootView.findViewById<TextView>(R.id.playlist_item_detail_author)
        a.text = channelTitle

        val tracksView = rootView.findViewById<TextView>(R.id.playlist_item_detail_tracks_count)
        tracksView.text = tracksCount.toString().plus(" Songs")
        val imageViewDetail = rootView.findViewById<ImageView>(R.id.playlist_item_detail_thumb)

        Glide.with(rootView.context)
            .load(thumbnail)
            .into(imageViewDetail)
    }


    private fun setupSearchView(rootView: View)
    {
        val videoSearch: SearchView = rootView.findViewById(R.id.video_search)
        videoSearch.setOnClickListener(View.OnClickListener { videoSearch.isIconified = false })

        val searchIcon = videoSearch.findViewById<ImageView>(R.id.search_mag_icon)
        searchIcon.setColorFilter(Color.WHITE)


        val cancelIcon = videoSearch.findViewById<ImageView>(R.id.search_close_btn)
        cancelIcon.setColorFilter(Color.WHITE)
        videoSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }

        })

        val textView = videoSearch.findViewById<TextView>(R.id.search_src_text)
        textView.setTextColor(Color.WHITE)
    }

    private fun setupUI(context: Context) {
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter =
            VideoListAdapter(
                arrayListOf(), this
            )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver(context: Context) {
        videoListViewModel.getVideolist().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ResultStatus.SUCCESS -> {
                    progressBar.visibility = View.GONE
                    it.data?.let { list -> renderList(list) }
                    recyclerView.visibility = View.VISIBLE
                }
                ResultStatus.LOADING -> {
                    progressBar.visibility = View.VISIBLE
                    recyclerView.visibility = View.GONE
                }
                ResultStatus.ERROR -> {
                    //Handle Error
                    progressBar.visibility = View.GONE
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(videoList: List<Video>) {
        adapter.addData(videoList)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel(context: Context) {
        val playlistId =  arguments?.getString("list_id")
        val author  = arguments?.getString("author")
        videoListViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                context,
                YtApiService,
                DatabaseHelperImpl(DatabaseBuilder.getInstance(context)),
                author ?: "",
                playlistId ?: ""
            )
        ).get(VideoListViewModel::class.java)
    }


    override fun onClickListener(playlistItem: PlaylistItem)
    {

    }
}