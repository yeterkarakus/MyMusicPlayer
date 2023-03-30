package com.yeterkarakus.miniyoutube.pushnotification.push

import android.app.NotificationManager
import com.yeterkarakus.miniyoutube.pushnotification.model.NotificationBuild


class Notifier (
    private val notificationManager : NotificationManager
){
    fun notify(notificationData: NotificationBuild){
        with(notificationData){
            notificationManager.notify(notificationId,systemNotification)
        }
    }
}