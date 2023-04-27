package com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.view


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
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
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.view.ArtistFragmentDirections.Companion.actionArtistFragmentToAlbumsFragment
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.view.ArtistFragmentDirections.Companion.actionArtistFragmentToSearchNotFoundFragment
import kotlinx.coroutines.*
import javax.inject.Inject


class ArtistFragment @Inject constructor(
    private val retrofit: RetrofitApi
): Fragment(),ArtistAlbumAdapter.OnItemClickListener {
    private var _binding: FragmentArtistBinding? = null
    private val binding get() = _binding!!
    private val args : ArtistFragmentArgs by navArgs()
    private lateinit var skeleton: Skeleton
    private var artistOverViewModel = ArtistOverViewModel()
    private var  albumsViewModel = AlbumsViewModel()
    private var artistViewModel = ArtistViewModel()
    private val trackUtils = TrackUtils()

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
        lifecycleScope.launch {
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
                    artistViewModel = trackUtils.getArtistViewModel(artistList)
                }
            }
            val getArtistOverView = retrofit.getArtistOverview(getArtist.body()!!.artists[0].id)
            val artistOverviewList: MutableList<ArtistOverViewModel> = mutableListOf()
            val albumList: MutableList<AlbumsViewModel> = mutableListOf()
            getArtistOverView.body()?.let {
                for (item in listOf(it.data.artist)) {

                    val artistOverViewModel = ArtistOverViewModel(
                        item.id,
                        item.uri,
                        item.profile.biography.text
                    )

                    artistOverviewList.add(artistOverViewModel)
                }
                artistOverViewModel = trackUtils.getArtistOverViewModel(artistOverviewList)
                if (it.data.artist.discography.albums.items.isNotEmpty()){
                    for (item in it.data.artist.discography.albums.items) {

                        val albumsViewModel = AlbumsViewModel(

                            item.releases.items[0].id,
                            item.releases.items[0].name,
                            item.releases.items[0].date.year,
                            item.releases.items[0].coverArt.sources[0].url
                        )
                        albumList.add(albumsViewModel)
                    }
                    albumsViewModel = trackUtils.getAlbumsViewModel(albumList)
                }
            }
            withContext(Dispatchers.Main) {
                binding.apply {
                    artistViewModel.let {
                        Glide.with(requireActivity())
                            .load(it.imageUrl.toString())
                            .transform(RoundedCorners(15))
                            .into(artistImg)
                    }
                    artistViewModel.let {
                        artistName.text = it.name
                    }
                    artistOverViewModel.let {
                        bioBody.text = it.bio
                    }
                    if (albumsViewModel.albumsId?.isNotEmpty() == true){
                        val adapter = ArtistAlbumAdapter(albumList, this@ArtistFragment)
                        singleRecyclerView.adapter = adapter
                        singleRecyclerView.layoutManager = LinearLayoutManager(context)

                    }
                    skeleton.showOriginal()
                }
            }
        }
    }

    override fun onAlbumsItemSelected(albumsViewModel: AlbumsViewModel) {
        if (albumsViewModel.albumsId != null){
            findNavController().navigate(actionArtistFragmentToAlbumsFragment(albumsViewModel.albumsId!!))
        }else{
            findNavController().navigate(actionArtistFragmentToSearchNotFoundFragment())
        }
    }
}

