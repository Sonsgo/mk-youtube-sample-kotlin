package com.mkabore.yt.ui.home

/**
 * Created by @author mkabore
 * 2020-10-25
 */

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mkabore.yt.R
import com.mkabore.yt.data.db.DatabaseBuilder
import com.mkabore.yt.data.db.DatabaseHelper
import com.mkabore.yt.data.db.DatabaseHelperImpl
import com.mkabore.yt.data.db.entity.User
import com.mkabore.yt.databinding.FragmentHomeBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel

    private lateinit var mDataBaseHelper: DatabaseHelper

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home,
            container,
            false
        )


        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        // Set the viewmodel for databinding - this allows the bound layout access
        // to all the data in the VieWModel
        binding.homeViewModel = homeViewModel

        // Specify the fragment view as the lifecycle owner of the binding.
        // This is used so that the binding can observe LiveData updates
        binding.lifecycleOwner = viewLifecycleOwner


        var view :View = binding.root;
        mDataBaseHelper =  DatabaseHelperImpl(DatabaseBuilder.getInstance(view.context))
        loadProfile()
        return view
    }

    private fun loadProfile()
    {
        GlobalScope.launch(Dispatchers.Main) {

            val users: List<User> = mDataBaseHelper.getUsers()
            if (users.isNotEmpty())
                run {
                    homeViewModel.updateUserData(users[0])
                    binding.executePendingBindings()
                }
        }
    }
}