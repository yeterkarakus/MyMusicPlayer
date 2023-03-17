package com.yeterkarakus.miniyoutube.view.searchpage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.yeterkarakus.miniyoutube.adapter.AlbumsRecyclerAdapter
import com.yeterkarakus.miniyoutube.adapter.TrackRecyclerAdapter
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.databinding.FragmentSearchActiveBinding
import com.yeterkarakus.miniyoutube.view.searchpage.albumsmodel.AlbumDetailsViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject


class SearchActiveFragment @Inject constructor(
    private val retrofit :RetrofitApi
): Fragment() {
    private var _binding: FragmentSearchActiveBinding? = null
    private val binding get() = _binding!!
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO +job)
    private val args: SearchActiveFragmentArgs by navArgs()
    private var albumDetailsViewModel: AlbumDetailsViewModel? = null

    private val albumList: MutableList<AlbumDetailsViewModel> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchActiveBinding.inflate(inflater, container, false)
        albumsData()
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        args.searchViewModel.let {
            val trackRecordCount = args.searchViewModel.trackRecordCount
            val albumRecordCount = args.searchViewModel.albumRecordCount

            val resultCount = trackRecordCount!! + albumRecordCount!!

            binding.resultCount.text = "${resultCount}\b Sonuç Bulundu "
            binding.keyword.text = "Aranan Kelime\b${args.searchViewModel.searchText.toString()}"
            val trackAdapter = TrackRecyclerAdapter(it.trackList!!)
            binding.tracksRecyclerView.adapter = trackAdapter
            binding.tracksRecyclerView.layoutManager = LinearLayoutManager(requireContext())

            val albumsAdapter = AlbumsRecyclerAdapter(it.albumList!!)
            binding.albumsRecyclerView.adapter = albumsAdapter
            binding.albumsRecyclerView.layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)


       }



    }

    private fun albumsData(){
        scope.launch {
            val getAlbum = retrofit.getAlbums("3t3BbpFJiGcXl4jI5CRLLA")
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
                albumDetailsViewModel?.let {viewModel->
                   val list =  albumList.toList()
                      viewModel.id = list[0].id
                      viewModel.name = list[0].name
                      viewModel.uri = list[0].uri
                      viewModel.artisId=list[0].artisId
                      viewModel.artistUri=list[0].artistUri
                      viewModel.imageUrl = list[0].imageUrl
                }

            }

        }

    }

override fun onDestroy() {

_binding = null
super.onDestroy()
}
}

