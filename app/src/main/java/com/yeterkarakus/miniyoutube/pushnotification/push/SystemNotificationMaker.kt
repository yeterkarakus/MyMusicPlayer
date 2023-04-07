package com.yeterkarakus.miniyoutube.pushnotification.push

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.Color
import android.media.MediaExtractor.MetricsConstants.TRACKS
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.navigation.NavDeepLinkBuilder
import com.yeterkarakus.miniyoutube.Constants.ALBUM
import com.yeterkarakus.miniyoutube.R
import com.yeterkarakus.miniyoutube.pushnotification.model.NotificationData
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.view.AlbumsFragmentArgs
import com.yeterkarakus.miniyoutube.view.searchpage.trackdetailsfragment.view.TrackDetailsFragmentArgs

class SystemNotificationMaker(private val context: Context) {
    private val chanelId = "firebase_push"
    private lateinit var pendingIntent: PendingIntent

    fun make(
        notificationData: NotificationData,
        notificationManager: NotificationManager
    ): Notification {
        notificationChanel(notificationManager)
        pendingIntent(notificationData.eventType,notificationData.uuid)
        return NotificationCompat.Builder(context, chanelId)
            .setContentTitle(notificationData.title)
            .setSmallIcon(R.drawable.notification_icon)
            .setContentText(notificationData.message)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()
    }

    private fun pendingIntent(eventType: String, uuid : String) {

        pendingIntent = when (eventType) {
            ALBUM -> {
                val args = Bundle()
                args.putString("uuid",uuid)
                AlbumsFragmentArgs.fromBundle(args)
                providerPendingIntent(R.id.albumsFragment,args)
            }
            TRACKS -> {

                val args = Bundle()
                args.putString("uuid",uuid)
                TrackDetailsFragmentArgs.fromBundle(args)
                providerPendingIntent(R.id.trackDetailsFragment )
            }
            else -> {
                providerPendingIntent(R.id.searchFragment)
            }
        }

     }

    private fun providerPendingIntent(setDestination: Int,args : Bundle? = null): PendingIntent =

        NavDeepLinkBuilder(context)
            .setArguments(args)
            .setGraph(R.navigation.navigation_graph)
            .setDestination(setDestination)
            .createPendingIntent()


    private fun notificationChanel(notificationManager: NotificationManager) {
        val chanelName = "firebase_push"
        val channel =
            NotificationChannel(chanelId, chanelName, NotificationManager.IMPORTANCE_HIGH).apply {
                description = "firebase_push"
                enableLights(true)
                lightColor = Color.MAGENTA
           }
        notificationManager.createNotificationChannel(channel)
    }
}


