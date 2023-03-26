package com.yeterkarakus.miniyoutube.view.searchpage


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeterkarakus.miniyoutube.TrackUtils
import com.yeterkarakus.miniyoutube.adapter.AlbumsRecyclerAdapter
import com.yeterkarakus.miniyoutube.adapter.TrackRecyclerAdapter
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.databinding.FragmentSearchActiveBinding
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.AlbumDetailsViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.AlbumTracksViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.BaseAlbumViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.model.AlbumViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.model.TrackViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.trackdetailsfragment.model.TrackDetailsViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


class SearchActiveFragment @Inject constructor (
    private val retrofit :RetrofitApi
    ): Fragment(), AlbumsRecyclerAdapter.OnItemClickListener,TrackRecyclerAdapter.OnItemClickListener {
    private var _binding: FragmentSearchActiveBinding? = null
    private val binding get() = _binding!!
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO +job)
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
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


       }


    }
    private fun trackDetailsData(id: String){
        scope.launch {

            var trackDetailsViewModel =  TrackDetailsViewModel()

            val getTracksData = retrofit.getTrack(id)
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
                val action = SearchActiveFragmentDirections.actionSearchActiveFragmentToTrackDetailsFragment(trackDetailsViewModel)
                findNavController().navigate(action)
            }

        }

    }

    private fun albumsData(id: String){


        scope.launch {
            val baseAlbumViewModel = BaseAlbumViewModel()

            val getAlbum = retrofit.getAlbums(id)
            val albumList: MutableList<AlbumDetailsViewModel> = mutableListOf()
            getAlbum.body()?.let {
                    for (item in it.albums) {
                        val album = AlbumDetailsViewModel(
                            item.id,
                            item.images[0].url,
                            item.name,
                            item.uri,
                            item.artists[0].id,
                            item.artists[0].name,
                            item.artists[0].uri
                        )
                        albumList.add(album)
                    }
            }
            baseAlbumViewModel.albumDetailsList = albumList

            val getTracks = retrofit.getAlbumTracks(id)
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
                    }
            baseAlbumViewModel.albumTracksList = albumsTrackList

            withContext(Dispatchers.Main){
                val action = SearchActiveFragmentDirections.actionSearchActiveFragmentToAlbumsFragment(baseAlbumViewModel)
                findNavController().navigate(action)

            }

        }

    }


    override fun onDestroy() {
        job.cancel()
        _binding = null
    super.onDestroy()
}

    override fun onItemSelect(albumViewModel: AlbumViewModel) {
        albumsData(albumViewModel.uri)

    }

    override fun onItemSelected(trackViewModel: TrackViewModel) {

        trackDetailsData(trackViewModel.id)

    }


}


