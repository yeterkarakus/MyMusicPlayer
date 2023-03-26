package com.yeterkarakus.miniyoutube.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.yeterkarakus.miniyoutube.adapter.ViewPagerAdapter
import com.yeterkarakus.miniyoutube.databinding.ActivityOnboardingBinding

class OnboardingActivity : AppCompatActivity() {
    private lateinit var binding: ActivityOnboardingBinding
    private val viewPagerAdapter = ViewPagerAdapter(supportFragmentManager,lifecycle)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            viewPager.adapter = viewPagerAdapter
            dotsIndicator.attachTo(viewPager)
            onboardingButton.setOnClickListener {
                val intent = Intent(this@OnboardingActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
                onboardingFinished()
            }
        }

    }

    private fun onboardingFinished(){
        val sharedPreferences = applicationContext.getSharedPreferences("Onboarding",Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }
}