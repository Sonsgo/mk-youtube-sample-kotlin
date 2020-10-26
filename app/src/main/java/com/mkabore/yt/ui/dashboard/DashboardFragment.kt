package com.mkabore.yt.ui.dashboard

/**
 * Created by @author mkabore
 * 2020-10-25
 */

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.mkabore.yt.R
import com.mkabore.yt.data.api.YtApiService
import com.mkabore.yt.data.db.DatabaseBuilder
import com.mkabore.yt.data.db.DatabaseHelperImpl
import com.mkabore.yt.data.db.entity.PlaylistItem
import com.mkabore.yt.databinding.FragmentDashboardBinding
import com.mkabore.yt.util.PlaylistItemClickListener
import com.mkabore.yt.util.ResultStatus
import com.mkabore.yt.util.ViewModelFactory
import kotlinx.android.synthetic.main.fragment_dashboard.*


class DashboardFragment : Fragment(), PlaylistItemClickListener {

   private lateinit var binding: FragmentDashboardBinding

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var adapter: PlaylistItemAdapter
    private lateinit var recyclerView: RecyclerView


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
          binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_dashboard,
            container,
            false
        )

        val rootView = binding.root;

        recyclerView = rootView.findViewById(R.id.recyclerView)

        setupUI(rootView.context)
        setupViewModel(rootView.context)
        setupObserver(rootView.context)

        return rootView
    }


    private fun setupUI(context: Context) {
        recyclerView.layoutManager = GridLayoutManager(context,2)
        adapter =
            PlaylistItemAdapter(
                arrayListOf(), this
            )
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as GridLayoutManager).orientation
            )
        )
        recyclerView.adapter = adapter
    }

    private fun setupObserver(context: Context) {
        dashboardViewModel.getPlaylists().observe(viewLifecycleOwner, Observer {
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

    private fun renderList(playlistItems: List<PlaylistItem>) {
        adapter.addData(playlistItems)
        adapter.notifyDataSetChanged()
    }

    private fun setupViewModel(context: Context) {
        dashboardViewModel = ViewModelProviders.of(
            this,
            ViewModelFactory(
                context,
                YtApiService,
                DatabaseHelperImpl(DatabaseBuilder.getInstance(context)),"",""
            )
        ).get(DashboardViewModel::class.java)
    }


    override fun onClickListener(playlistItem: PlaylistItem)
    {
        val bundle = bundleOf("list_id" to playlistItem.list_id,
            "author" to playlistItem.title,
            "thumbnail_url" to playlistItem.thumbnailUrl,
            "channel_title" to playlistItem.channelTitle,
            "tracks_count" to playlistItem.tracksCount)


        findNavController().navigate(
            R.id.dashboard_to_playlist, bundle
        )
    }
}