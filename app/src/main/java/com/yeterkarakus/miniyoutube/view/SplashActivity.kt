package com.yeterkarakus.miniyoutube.view
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yeterkarakus.miniyoutube.R
import com.yeterkarakus.miniyoutube.databinding.ActivitySplashBinding
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.view.AlbumsFragmentArgs

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.d("Device Density", "onCreate: " + resources.displayMetrics.density)

        Handler(Looper.getMainLooper()).postDelayed({
            if (onboardingFinished()){
            val intentMain = Intent(this@SplashActivity, MainActivity::class.java)

                intentMain.putExtra("isFromPush", false)

                if (intent.getStringExtra("uuid") != null) {
                    intentMain.putExtra("isFromPush", true)
                    intentMain.putExtra("uuid", intent.getStringExtra("uuid"))
                }

                if (intent.getStringExtra("eventType") != null) {
                    intentMain.putExtra("eventType", intent.getStringExtra("eventType"))
                }

            startActivity(intentMain)
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