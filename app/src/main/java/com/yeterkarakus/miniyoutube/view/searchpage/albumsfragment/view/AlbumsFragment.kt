package com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yeterkarakus.miniyoutube.TrackUtils
import com.yeterkarakus.miniyoutube.adapter.AlbumTracksRecyclerAdapter
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.databinding.FragmentAlbumsBinding
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.AlbumTracksViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.trackdetailsfragment.model.TrackDetailsViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


class AlbumsFragment @Inject constructor(
    private val retrofit: RetrofitApi
) : Fragment(),AlbumTracksRecyclerAdapter.OnItemClickListener {
    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO+job)
    private val args : AlbumsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        args.baseAlbumViewModel.let {
            Glide.with(requireActivity())
                .load(it.albumDetailsList!!.get(0).imageUrl)
                .transform(RoundedCorners(15))
                .into(binding.albumDetailsImage)

            val albumTrackRecyclerAdapter = AlbumTracksRecyclerAdapter(it.albumTracksList!!,this)
            binding.trackListRecyclerView.adapter = albumTrackRecyclerAdapter
            binding.trackListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        }

    }


    private fun getTracksData(uri : String){

        scope.launch {
            var trackDetailsViewModel =  TrackDetailsViewModel()
            val getTracksData = retrofit.getTrack(uri)
            val trackList: MutableList<TrackDetailsViewModel> = mutableListOf()
            getTracksData.body()?.let {

                for (item in it.tracks) {
                    val track = TrackDetailsViewModel(
                        item.album.images[0].url,
                        item.id,
                        item.name,
                        item.artists[0].id,
                        item.artists[0].name,
                        item.album.images[0].url,
                        item.album.name,
                        item.album.id
                    )
                    trackList.add(track)
                }

                val trackUtils = TrackUtils()
                trackDetailsViewModel = trackUtils.getTracksDetailsViewModel(trackList)

            }
            withContext(Dispatchers.Main){
                if(getTracksData.isSuccessful) {
                    val action = AlbumsFragmentDirections
                        .actionAlbumsFragmentToTrackDetailsFragment(trackDetailsViewModel)
                    findNavController().navigate(action)
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
        _binding = null
    }

    override fun onAlbumTrackItemsSelect(albumTracksViewModel: AlbumTracksViewModel) {

        getTracksData(albumTracksViewModel.uri)
    }
}
