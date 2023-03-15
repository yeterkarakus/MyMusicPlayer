package com.yeterkarakus.miniyoutube.view.searchpage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

import com.yeterkarakus.miniyoutube.R
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.databinding.FragmentArtistBinding
import com.yeterkarakus.miniyoutube.databinding.FragmentSearchActiveBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActiveFragment @Inject constructor(
    private val retrofit: RetrofitApi
): Fragment() {
    private var _binding: FragmentSearchActiveBinding? = null
    private val binding get() = _binding!!
    private val job = Job()
    private val scope = CoroutineScope(Dispatchers.IO + job)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchActiveBinding.inflate(inflater, container, false)
        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onDestroy() {
        job.cancel()
        _binding = null
        super.onDestroy()
    }
}