package com.yeterkarakus.miniyoutube.view.searchpage.trackdetailsfragment.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.faltenreich.skeletonlayout.Skeleton
import com.yeterkarakus.miniyoutube.TrackUtils
import com.yeterkarakus.miniyoutube.databinding.FragmentTrackDetailsBinding
import com.yeterkarakus.miniyoutube.view.searchpage.trackdetailsfragment.model.TrackDetailsViewModel
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
    private lateinit var skeleton: Skeleton
    private var trackDetailsViewModel = TrackDetailsViewModel()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTrackDetailsBinding.inflate(inflater, container, false)
        skeleton = binding.trackSkeletonLayout
        skeleton.showSkeleton()
        return binding.root
    }

   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tracksData()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        job.cancel()
        _binding = null
    }

    private fun tracksData(){
        Log.e("TAG", args.uuid)

        scope.launch {
            val getTracksData = retrofit.getTrack(args.uuid)
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
                binding.apply {
                    Glide.with(requireActivity())
                        .load(trackDetailsViewModel.tracksImgUrl)
                        .transform(RoundedCorners(15))
                        .into(trackDetailsImg)
                    trackName.text = trackDetailsViewModel.name
                    artistName.text =trackDetailsViewModel.artistName
                    albumName.text = trackDetailsViewModel.albumName


                    Glide.with(requireActivity())
                        .load(trackDetailsViewModel.albumImgUrl)
                        .transform(RoundedCorners(15))
                        .into(albumImg)
                    detailsButton.setOnClickListener{
                        val action = TrackDetailsFragmentDirections
                            .actionTrackDetailsFragmentToArtistFragment(trackDetailsViewModel.artistId.toString())
                        findNavController().navigate(action)

                    }
                }
                skeleton.showOriginal()
            }
        }
    }
 }





