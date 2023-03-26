package com.yeterkarakus.miniyoutube.view

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.yeterkarakus.miniyoutube.api.RetrofitApi
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.view.AlbumsFragment
import com.yeterkarakus.miniyoutube.view.searchpage.SearchActiveFragment
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.view.ArtistFragment
import com.yeterkarakus.miniyoutube.view.searchpage.trackdetailsfragment.view.TrackDetailsFragment
import com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.view.SearchFragment
import javax.inject.Inject

class MiniYoutubeFragmentFactory @Inject constructor(
    private val retrofit: RetrofitApi
) : FragmentFactory() {
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when (className) {
            SearchFragment::class.java.name-> SearchFragment(retrofit)
            SearchActiveFragment::class.java.name-> SearchActiveFragment(retrofit)
            AlbumsFragment::class.java.name-> AlbumsFragment(retrofit)
            TrackDetailsFragment::class.java.name-> TrackDetailsFragment(retrofit)
            ArtistFragment::class.java.name->ArtistFragment(retrofit)
            else -> return super.instantiate(classLoader, className)

        }
    }
}