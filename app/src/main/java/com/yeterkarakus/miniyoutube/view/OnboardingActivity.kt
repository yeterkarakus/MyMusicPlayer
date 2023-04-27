package com.yeterkarakus.miniyoutube.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.RequiresApi
import com.yeterkarakus.miniyoutube.adapter.ViewPagerAdapter
import com.yeterkarakus.miniyoutube.databinding.ActivityOnboardingBinding
import java.security.Permission


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