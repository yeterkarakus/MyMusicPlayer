package com.yeterkarakus.miniyoutube.pushnotification.push

import android.app.NotificationManager
import com.yeterkarakus.miniyoutube.pushnotification.model.NotificationBuild
import com.yeterkarakus.miniyoutube.pushnotification.model.NotificationData
import java.util.*

class NotificationBuilder( private val systemNotificationMaker: SystemNotificationMaker) {

    fun build(notificationData : NotificationData, notificationManager: NotificationManager) : NotificationBuild {
        val systemNotification = systemNotificationMaker.make(notificationData,notificationManager)
        val notificationId = Random().nextInt()
        return NotificationBuild(notificationId,systemNotification)
    }
}
