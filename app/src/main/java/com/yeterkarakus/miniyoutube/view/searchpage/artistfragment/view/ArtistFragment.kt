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
import com.faltenreich.skeletonlayout.Skeleton
import com.yeterkarakus.miniyoutube.TrackUtils
import com.yeterkarakus.miniyoutube.adapter.ArtistAlbumAdapter
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.databinding.FragmentArtistBinding
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.AlbumsViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.ArtistOverViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.ArtistViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


class ArtistFragment @Inject constructor(
    private val retrofit: RetrofitApi
): Fragment(),ArtistAlbumAdapter.OnItemClickListener {
    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val args : ArtistFragmentArgs by navArgs()
    private lateinit var skeleton: Skeleton
    private var artistOverViewModel = ArtistOverViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArtistBinding.inflate(inflater, container, false)
        skeleton = binding.artistSkeletonLayout
        skeleton.showSkeleton()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data()
    }
    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun data() {
        scope.launch {

            val getArtist = retrofit.getArtist(args.uuid)
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
            }

            val getArtistOverview = retrofit.getArtistOverview(artistList[0].id)
            val artistOverviewList: MutableList<ArtistOverViewModel> = mutableListOf()
            val albumList: MutableList<AlbumsViewModel> = mutableListOf()
            getArtistOverview.body()?.let {
                for (item in listOf(it.data.artist)) {

                    val artistOverViewModel = ArtistOverViewModel(
                        item.id,
                        item.uri,
                        item.profile.biography.text
                    )

                    artistOverviewList.add(artistOverViewModel)
                }
                val trackUtils = TrackUtils()


                artistOverViewModel = trackUtils.getArtistOverViewModel(artistOverviewList)
                for (item in it.data.artist.discography.albums.items) {
                    val albumsViewModel = AlbumsViewModel(
                        item.releases.items[0].id,
                        item.releases.items[0].name,
                        item.releases.items[0].date.year,
                        item.releases.items[0].coverArt.sources[0].url
                    )
                    albumList.add(albumsViewModel)
                }
            }


            withContext(Dispatchers.Main) {
                binding.apply {
                    Glide.with(requireActivity())
                        .load(albumList[0].albumsImgUrl)
                        .transform(RoundedCorners(15))
                        .into(artistImg)
                    artistName.text = artistList[0].name
                    bioBody.text = artistOverviewList[0].bio

                    val adapter = ArtistAlbumAdapter(albumList, this@ArtistFragment)
                    singleRecyclerView.adapter = adapter
                    singleRecyclerView.layoutManager = LinearLayoutManager(context)
                }
                skeleton.showOriginal()
            }
        }
    }

    override fun onAlbumsItemSelected(albumsViewModel: AlbumsViewModel) {
        val action = ArtistFragmentDirections.actionArtistFragmentToAlbumsFragment(albumsViewModel.albumsId)
        findNavController().navigate(action)
    }
}

