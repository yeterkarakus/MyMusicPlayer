package com.yeterkarakus.miniyoutube

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MiniYoutubeApplication : Application() {

    override fun onCreate() {
        subscribe()
        super.onCreate()

    }

    private fun subscribe(){
        FirebaseMessaging.getInstance().subscribeToTopic("test")

        FirebaseMessaging.getInstance().token.addOnCompleteListener OnCompleteListener@{
            if (!it.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", it.exception)
                return@OnCompleteListener
            }
        }
    }

}