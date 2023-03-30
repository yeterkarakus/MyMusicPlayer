package com.yeterkarakus.miniyoutube.view.searchpage.trackdetailsfragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.yeterkarakus.miniyoutube.databinding.FragmentTrackDetailsBinding
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.ArtistOverViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.ArtistViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.BaseViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.AlbumsViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


class TrackDetailsFragment @Inject constructor(
    private val retrofit: RetrofitApi
) : Fragment() {
    private var _binding: FragmentTrackDetailsBinding? = null
    private val binding get() = _binding!!
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO+job)
    private val args : TrackDetailsFragmentArgs by navArgs()




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackDetailsBinding.inflate(inflater, container, false)

        return binding.root
    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            Glide.with(view).load(args.trackDetailsViewModel.tracksImgUrl)
                .transform(RoundedCorners(15))
                .into(trackDetailsImg)
            trackName.text = args.trackDetailsViewModel.name
            artistName.text = args.trackDetailsViewModel.artistName
            albumName.text = args.trackDetailsViewModel.albumName
            Glide.with(view)
                .load(args.trackDetailsViewModel.albumImgUrl)
                .transform(RoundedCorners(15))
                .into(albumImg)

            detailsButton.setOnClickListener{
                data()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
        _binding = null
    }


    private fun data(){
        scope.launch {

            val getArtist = retrofit.getArtist(args.trackDetailsViewModel.artistId.toString())
            val artistList: MutableList<ArtistViewModel> = mutableListOf()
            if (getArtist.isSuccessful) {
                getArtist.body()?.let {
                    for (item in it.artists) {
                        val artistViewModel = ArtistViewModel(
                            item.id,
                            item.name,
                            item.uri,
                            item.images[0].url
                        )
                        artistList.add(artistViewModel)
                    }
                }
                val getArtistOverview = retrofit.getArtistOverview(artistList[0].id)
                val artistOverviewList: MutableList<ArtistOverViewModel> = mutableListOf()
                val albumList : MutableList<AlbumsViewModel> = mutableListOf()
                getArtistOverview.body()?.let {

                    for (item in listOf(it.data.artist)) {
                        val artistOverViewModel = ArtistOverViewModel(
                            item.id,
                            item.uri,
                            item.profile.biography.text
                        )

                        artistOverviewList.add(artistOverViewModel)
                    }
                    for (item in it.data.artist.discography.albums.items){
                        val albumsViewModel = AlbumsViewModel(
                            item.releases.items[0].id,
                            item.releases.items[0].name,
                            item.releases.items[0].date.year,
                            item.releases.items[0].coverArt.sources[0].url
                        )
                        albumList.add(albumsViewModel)
                    }
                }
                val baseViewModel = BaseViewModel(artistList, artistOverviewList, albumList)
                withContext(Dispatchers.Main)
                {
                    val action =
                        TrackDetailsFragmentDirections.actionTrackDetailsFragmentToArtistFragment(
                            baseViewModel
                        )
                    findNavController().navigate(action)

                }
            }
        }
    }
 }





