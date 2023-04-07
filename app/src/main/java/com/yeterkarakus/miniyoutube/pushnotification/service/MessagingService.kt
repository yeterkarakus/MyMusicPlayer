package com.yeterkarakus.miniyoutube.pushnotification.service

import android.app.NotificationManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.yeterkarakus.miniyoutube.pushnotification.push.*


class MessagingService: FirebaseMessagingService(){
    private lateinit var handler : NotificationHandler
    override fun onCreate() {
        super.onCreate()
        val resolver = RemoteMessageResolver()
        val systemNotificationMaker = SystemNotificationMaker(this)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationBuilder(systemNotificationMaker)
        val notifier = Notifier(notificationManager)
        handler = NotificationHandler(resolver, builder, notifier, notificationManager)
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)

    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        handler.let {
            handler.handle(message.data)
        }
    }


}