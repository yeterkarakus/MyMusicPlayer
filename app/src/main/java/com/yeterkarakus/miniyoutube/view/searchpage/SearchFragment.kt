package com.yeterkarakus.miniyoutube.view.searchpage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.databinding.FragmentSearchBinding
import com.yeterkarakus.miniyoutube.model.searchmodel.SearchType
import com.yeterkarakus.miniyoutube.view.searchpage.model.AlbumViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.model.SearchViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.model.TrackViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

class SearchFragment @Inject constructor(
    private val retrofit : RetrofitApi
) : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private val trackList = mutableListOf<TrackViewModel>()


   // private val actionNotFound = SearchFragmentDirections.actionSearchFragmentToSearchNotFoundFragment()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.searchEditText.setOnEditorActionListener { v, actionId, keyEvent ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                binding.imageView.visibility = GONE
                binding.lottieLoading.visibility = VISIBLE
                getSearchData()
            }
            false
        }

    }

    private fun getSearchData() {
        scope.launch {
            val searchTracksData = retrofit.search(
                binding.searchEditText.text.toString(),
                SearchType.tracks,
                limit = 10
            )
            val searchAlbumData = retrofit.search(
                binding.searchEditText.text.toString(),
                SearchType.albums,
                limit = 5
            )

            val searchViewModel = SearchViewModel()
            searchViewModel.searchText = binding.searchEditText.text.toString()

            //Track
            searchTracksData.body()?.let {
                for (item in it.tracks.items) {
                    val track = TrackViewModel(
                        item.data.id,
                        item.data.name,
                        item.data.artists.items[0].profile.name,
                        item.data.albumOfTrack.coverArt.sources[0].url
                    )
                    trackList.add(track)
                }

                searchViewModel.trackRecordCount =it.tracks.totalCount
            }
            searchViewModel.trackList = trackList
            //Track

            //Album

            val albumList : MutableList<AlbumViewModel> = mutableListOf()
            searchAlbumData.body()?.let {

                for (item in it.albums.items) {

                    val album = AlbumViewModel(
                        item.data.uri,
                        item.data.name,
                        item.data.artists.items[0].profile.name,
                        item.data.coverArt.sources[0].url
                    )
                    albumList.add(album)
                }
                searchViewModel.albumRecordCount = it.albums.totalCount
            }
            searchViewModel.albumList = albumList


            //Album

            //TODO: SearchViewModel datasını fragmente taşı, RecyclerView leri oluştur vs vs.
            if (searchAlbumData.isSuccessful&&searchTracksData.isSuccessful) {
            withContext(Dispatchers.Main){
                    val actionSearchActive = SearchFragmentDirections.actionSearchFragmentToSearchActiveFragment(searchViewModel)
                    findNavController().navigate(actionSearchActive) }

            }
        }
    }

    override fun onDestroy() {
        _binding = null
        job.cancel()
        super.onDestroy()
    }
}

