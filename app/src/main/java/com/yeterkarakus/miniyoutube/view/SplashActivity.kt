package com.yeterkarakus.miniyoutube.view
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.yeterkarakus.miniyoutube.R
import com.yeterkarakus.miniyoutube.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("Device Density", "onCreate: " + getResources().getDisplayMetrics().density)

        Handler(Looper.getMainLooper()).postDelayed({
            if (onboardingFinished()){
            val intent = Intent(this@SplashActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
            } else {
                val intent = Intent(this@SplashActivity, OnboardingActivity::class.java)
                startActivity(intent)
                finish()
            }
        },2000)


        val animation = AnimationUtils.loadAnimation(applicationContext, R.anim.anim)
        binding.apply {
            miniYoutubeText.animation = animation
            splashImage.animation = animation
        }

    }
    private fun onboardingFinished() : Boolean {
        val sharedPreferences = applicationContext.getSharedPreferences("Onboarding", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("Finished",false)
    }
}