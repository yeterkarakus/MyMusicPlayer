package com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.view

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.speech.RecognizerIntent
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.databinding.FragmentSearchBinding
import com.yeterkarakus.miniyoutube.model.common.ErrorResponse
import com.yeterkarakus.miniyoutube.model.searchmodel.SearchType
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.model.AlbumViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.model.SearchViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.model.TrackViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.view.SearchFragmentDirections.Companion.actionSearchFragmentToSearchActiveFragment
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.view.SearchFragmentDirections.Companion.actionSearchFragmentToSearchNotFoundFragment
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

class SearchFragment @Inject constructor(
    private val retrofit : RetrofitApi
) : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var permissionLauncher: ActivityResultLauncher<String>
    private val gson = Gson()
    private var errorResponse : ErrorResponse? = null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        registerLauncher()
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                try {
                    binding.imageView.visibility = GONE
                    binding.lottieLoading.visibility = VISIBLE
                    getSearchData()
                } catch (e: Exception) {
                    e.printStackTrace()
                }

            }
            false
        }


        binding.imageView.setOnClickListener {
            speechToText(it)

        }
    }


    private fun speechToText(view: View){
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.RECORD_AUDIO)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.RECORD_AUDIO)){
                Snackbar.make(view,"Permission needed ", Snackbar.LENGTH_INDEFINITE).setAction("Give Permission"){
                    permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)

                }.show()
            }else{
                permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
            }
        }else{
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak")

            activityResultLauncher.launch(intent)

        }

    }
    private fun registerLauncher(){
        activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if (it.resultCode == RESULT_OK) {
                val result = it.data
                result?.let { intent ->
                    val res: ArrayList<String> = intent.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS) as ArrayList<String>
                    binding.searchEditText.setText(Objects.requireNonNull(res)[0])
                }
            }
        }
        permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            intent.putExtra(
                RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
            )
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak")
            if (it){
                activityResultLauncher.launch(intent)
            }
        }
    }


    private fun getSearchData() {

        val type = object : TypeToken<ErrorResponse>() {}.type
        lifecycleScope.launch {
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
                val trackList = mutableListOf<TrackViewModel>()
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
                    searchViewModel.trackRecordCount = it.tracks.totalCount
                    searchViewModel.trackList = trackList
                }


                //Album

                val albumList: MutableList<AlbumViewModel> = mutableListOf()
                searchAlbumData.body()?.let {

                    for (item in it.albums.items) {

                        val album = AlbumViewModel(

                            item.data.uri.replace("spotify:album:", ""),
                            item.data.name,
                            item.data.artists.items[0].profile.name,
                            item.data.coverArt.sources[0].url
                        )
                        albumList.add(album)
                    }
                    searchViewModel.albumRecordCount = it.albums.totalCount
                    searchViewModel.albumList = albumList
                }
                withContext(Dispatchers.Main) {
                    if (searchAlbumData.isSuccessful && searchTracksData.isSuccessful){
                        findNavController().navigate(actionSearchFragmentToSearchActiveFragment(searchViewModel))
                    }else {
                        errorResponse = gson.fromJson(searchTracksData.errorBody()!!.charStream(), type)
                        errorResponse?.let {
                            findNavController().navigate(actionSearchFragmentToSearchNotFoundFragment())
                            Toast.makeText(requireContext(), it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                }
        }
    }




    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}