package com.yeterkarakus.miniyoutube

import android.app.Application
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MiniYoutubeApplication : Application() {
    /*
    override fun onCreate() {
        subscribe()
        super.onCreate()
    }

    private fun subscribe(){
        FirebaseMessaging.getInstance().token
    }
    
     */
}