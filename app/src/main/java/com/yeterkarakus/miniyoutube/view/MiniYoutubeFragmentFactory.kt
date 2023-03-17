package com.yeterkarakus.miniyoutube.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.view.searchpage.AlbumsFragment
import com.yeterkarakus.miniyoutube.view.searchpage.ArtistFragment
import com.yeterkarakus.miniyoutube.view.searchpage.SearchActiveFragment
import com.yeterkarakus.miniyoutube.view.searchpage.SearchFragment
import javax.inject.Inject

class MiniYoutubeFragmentFactory @Inject constructor(
    private val retrofit: RetrofitApi
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            ArtistFragment::class.java.name -> ArtistFragment(retrofit)
            SearchFragment::class.java.name->SearchFragment(retrofit)
            SearchActiveFragment::class.java.name->SearchActiveFragment(retrofit)
            else -> return super.instantiate(classLoader, className)

        }
    }
}