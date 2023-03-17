package com.yeterkarakus.miniyoutube.view.searchpage


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.databinding.FragmentArtistBinding
import com.yeterkarakus.miniyoutube.model.searchmodel.SearchType
import kotlinx.coroutines.*
import javax.inject.Inject


class ArtistFragment @Inject constructor(
    private val retrofit : RetrofitApi
): Fragment() {
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    private var _binding: FragmentArtistBinding? = null

    private val binding get() = _binding!!

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

        scope.launch {
            val search = retrofit.search("Haluk levent", SearchType.albums)
            withContext(Dispatchers.Main){
                search.body()?.let {
                    binding.nameText.text = it.albums.items[0].data.date.year.toString()
                }
            }
        }
    }

    override fun onDestroy() {
        job.cancel()
        _binding = null
        super.onDestroy()
    }
}

