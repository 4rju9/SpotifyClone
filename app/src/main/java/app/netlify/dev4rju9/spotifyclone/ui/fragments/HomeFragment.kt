package app.netlify.dev4rju9.spotifyclone.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import app.netlify.dev4rju9.spotifyclone.R
import app.netlify.dev4rju9.spotifyclone.adapters.SongAdapter
import app.netlify.dev4rju9.spotifyclone.databinding.FragmentHomeBinding
import app.netlify.dev4rju9.spotifyclone.other.Status
import app.netlify.dev4rju9.spotifyclone.ui.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var mainViewModel: MainViewModel
    @Inject
    lateinit var songAdapter: SongAdapter
    private lateinit var viewBinding : FragmentHomeBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewBinding = FragmentHomeBinding.bind(view)
        setupRecyclerView()
        subscribeToObservers()

        songAdapter.setOnItemClickListener { song ->
            mainViewModel.playOrToggleSong(song)
        }
    }

    private fun setupRecyclerView () = viewBinding.rvAllSongs.apply {
        adapter = songAdapter
        layoutManager = LinearLayoutManager(requireActivity())
    }

    private fun subscribeToObservers () {
        mainViewModel.mediaItems.observe(viewLifecycleOwner) { result ->
            when (result.status) {
                Status.SUCCESS -> {
                    viewBinding.allSongsProgressBar.isVisible = false
                    result.data?.let { songs ->
                        songAdapter.songs = songs
                    }
                }
                Status.LOADING -> {
                    viewBinding.allSongsProgressBar.isVisible = true
                }
                Status.ERROR -> Unit
            }
        }
    }

}