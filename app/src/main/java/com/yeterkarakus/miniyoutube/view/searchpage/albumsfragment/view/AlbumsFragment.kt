package com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faltenreich.skeletonlayout.Skeleton
import com.yeterkarakus.miniyoutube.TrackUtils
import com.yeterkarakus.miniyoutube.adapter.AlbumTracksRecyclerAdapter
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.databinding.FragmentAlbumsBinding
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.AlbumDetailsViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.AlbumTracksViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


class AlbumsFragment @Inject constructor(
    private val retrofit: RetrofitApi
) : Fragment(),AlbumTracksRecyclerAdapter.OnItemClickListener {
    private lateinit var skeleton: Skeleton
    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO+job)
    private val args by navArgs<AlbumsFragmentArgs>()
    private var albumTracksViewModel = AlbumTracksViewModel()
    private var albumDetailsViewModel = AlbumDetailsViewModel()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        skeleton = binding.albumSkeletonLayout
        skeleton.showSkeleton()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        albumsData()
        }


    private fun albumsData(){
        scope.launch {
            val trackUtil = TrackUtils()

            val albumList: MutableList<AlbumDetailsViewModel> = mutableListOf()
            val getAlbum = retrofit.getAlbums(args.uuid)
            getAlbum.body()?.let {
                for (item in it.albums) {
                    val album = AlbumDetailsViewModel(
                        item.id,
                        item.images[0].url,
                        item.name,
                        item.uri,
                        item.artists[0].name,
                        item.artists[0].uri
                    )
                    albumList.add(album)
                }
               albumDetailsViewModel = trackUtil.getAlbumDetailsViewModel(albumList)
            }


            val getTracks = retrofit.getAlbumTracks(args.uuid)
            val albumsTrackList : MutableList<AlbumTracksViewModel> = mutableListOf()
            getTracks.body()?.let {
                for (item in it.data.album.tracks.items){
                    val tracks = AlbumTracksViewModel(
                        item.track.uri.replace("spotify:track:",""),
                        item.track.name,
                        item.track.trackNumber,
                        item.track.artists.items[0].profile.name
                    )
                    albumsTrackList.add(tracks)
                }
                albumTracksViewModel = trackUtil.getAlbumTracksViewModel(albumsTrackList)
            }

            withContext(Dispatchers.Main){
                Glide.with(requireActivity())
                    .load(albumList[0].imageUrl)
                    .transform(RoundedCorners(15))
                    .into(binding.albumDetailsImage)
                binding.albumName.text = albumList[0].name

                val albumTrackRecyclerAdapter = AlbumTracksRecyclerAdapter(albumsTrackList,
                    this@AlbumsFragment)
                binding.trackListRecyclerView.adapter = albumTrackRecyclerAdapter
                binding.trackListRecyclerView.layoutManager = LinearLayoutManager(requireContext())

                skeleton.showOriginal()

            }

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
        _binding = null
    }


    override fun onAlbumTrackItemsSelect(albumTracksViewModel: AlbumTracksViewModel) {
        val action = AlbumsFragmentDirections
            .actionAlbumsFragmentToTrackDetailsFragment(albumTracksViewModel.uri.toString())
        findNavController().navigate(action)
    }
}




