package com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.view


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
import com.yeterkarakus.miniyoutube.adapter.ArtistAlbumAdapter
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.databinding.FragmentArtistBinding
import com.yeterkarakus.miniyoutube.view.searchpage.SearchActiveFragmentDirections
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.AlbumDetailsViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.AlbumTracksViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.BaseAlbumViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.AlbumsViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


class ArtistFragment @Inject constructor(
    private val retrofitApi: RetrofitApi
): Fragment(),ArtistAlbumAdapter.OnItemClickListener {
    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val args : ArtistFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        return binding.root
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            Glide.with(requireActivity())
                .load(args.baseViewModel.artistList[0].imageUrl)
                .transform(RoundedCorners(15))
                .into(artistImg)
            artistName.text = args.baseViewModel.artistList[0].name
            bioBody.text = args.baseViewModel.artistOverviewList[0].bio

            val adapter = ArtistAlbumAdapter(args.baseViewModel.albumsList,this@ArtistFragment)
            singleRecyclerView.adapter = adapter
            singleRecyclerView.layoutManager = LinearLayoutManager(context)


        }

    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
    private fun albumsData(id: String){


        scope.launch {
            val baseAlbumViewModel = BaseAlbumViewModel()

            val getAlbum = retrofitApi.getAlbums(id)
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

            val getTracks = retrofitApi.getAlbumTracks(id)
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
                val action = ArtistFragmentDirections.actionArtistFragmentToAlbumsFragment(baseAlbumViewModel)
                findNavController().navigate(action)

            }

        }

    }

    override fun onAlbumsItemSelected(albumsViewModel: AlbumsViewModel) {
        albumsData(albumsViewModel.albumsId)
    }
}

