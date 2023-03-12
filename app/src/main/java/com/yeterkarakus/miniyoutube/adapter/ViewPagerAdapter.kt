package com.yeterkarakus.miniyoutube.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yeterkarakus.miniyoutube.onboarding.FirstOnboardingScreenFragment
import com.yeterkarakus.miniyoutube.onboarding.SecondOnboardingScreenFragment
import com.yeterkarakus.miniyoutube.onboarding.ThirdOnboardingScreenFragment

class ViewPagerAdapter(fragmentManager:FragmentManager,lifecycle : Lifecycle )
    : FragmentStateAdapter(fragmentManager,lifecycle) {

    private val numPages = 3
    override fun getItemCount(): Int {
        return numPages
    }

    override fun createFragment(position: Int): Fragment {

        when (position) {
            0 -> return FirstOnboardingScreenFragment()
            1 -> return SecondOnboardingScreenFragment()
            2 -> return ThirdOnboardingScreenFragment()
        }
        return Fragment()
    }
}