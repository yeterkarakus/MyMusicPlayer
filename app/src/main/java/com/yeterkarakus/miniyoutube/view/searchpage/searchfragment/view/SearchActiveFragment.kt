package com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.view


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeterkarakus.miniyoutube.adapter.AlbumsRecyclerAdapter
import com.yeterkarakus.miniyoutube.adapter.TrackRecyclerAdapter
import com.yeterkarakus.miniyoutube.databinding.FragmentSearchActiveBinding
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.model.AlbumViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.model.TrackViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.view.SearchActiveFragmentDirections.Companion.actionSearchActiveFragmentToAlbumsFragment
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.view.SearchActiveFragmentDirections.Companion.actionSearchActiveFragmentToSearchFragment
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.view.SearchActiveFragmentDirections.Companion.actionSearchActiveFragmentToSearchNotFoundFragment
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.view.SearchActiveFragmentDirections.Companion.actionSearchActiveFragmentToTrackDetailsFragment
import kotlinx.coroutines.*


class SearchActiveFragment: Fragment()
    ,AlbumsRecyclerAdapter.OnItemClickListener
    ,TrackRecyclerAdapter.OnItemClickListener {

    private var _binding: FragmentSearchActiveBinding? = null
    private val binding get() = _binding!!
    private val args: SearchActiveFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchActiveBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    binding.searchButton.setOnClickListener {
        findNavController().navigate(actionSearchActiveFragmentToSearchFragment())
    }

        args.searchViewModel.let {
            val trackRecordCount = args.searchViewModel.trackRecordCount
            val albumRecordCount = args.searchViewModel.albumRecordCount

            val resultCount = trackRecordCount!! + albumRecordCount!!

            binding.resultCount.text = "${resultCount}\b Sonuç Bulundu "
            binding.keyword.text = "Aranan Kelime\b${args.searchViewModel.searchText.toString()}"

            val trackAdapter = TrackRecyclerAdapter(it.trackList!!,this)
            binding.tracksRecyclerView.adapter = trackAdapter
            binding.tracksRecyclerView.layoutManager = LinearLayoutManager(requireContext())

            val albumsAdapter = AlbumsRecyclerAdapter(it.albumList!!, this)
            binding.albumsRecyclerView.adapter = albumsAdapter
            binding.albumsRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(),
                LinearLayoutManager.HORIZONTAL, false
                )
       }
    }
    override fun onDestroy() {
        _binding = null
    super.onDestroy()
}

    override fun onItemSelected(trackViewModel: TrackViewModel) {
        if (trackViewModel.id.isNotEmpty()){
            findNavController().navigate(actionSearchActiveFragmentToTrackDetailsFragment(trackViewModel.id))
        }else{
            findNavController().navigate(actionSearchActiveFragmentToSearchNotFoundFragment())
        }
    }

    override fun onItemSelect(albumViewModel: AlbumViewModel) {
        if (albumViewModel.uri.isNotEmpty()){
            findNavController().navigate(actionSearchActiveFragmentToAlbumsFragment(albumViewModel.uri))
        }else {
           findNavController().navigate(actionSearchActiveFragmentToSearchNotFoundFragment())
        }

    }
}


