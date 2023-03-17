package com.yeterkarakus.miniyoutube.view

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.yeterkarakus.miniyoutube.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : FragmentActivity() {
    @Inject
    lateinit var fragmentFactory: MiniYoutubeFragmentFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
    }
}