package com.yeterkarakus.miniyoutube.pushnotification.push

import com.yeterkarakus.miniyoutube.Constants.ALBUM
import com.yeterkarakus.miniyoutube.Constants.EVENT_TYPE
import com.yeterkarakus.miniyoutube.Constants.TRACK
import com.yeterkarakus.miniyoutube.pushnotification.model.NotificationData

class RemoteMessageResolver {
    fun resolve(remoteMessage : Map<String,String>) : NotificationData {
        val title = remoteMessage["title"]
        val message = remoteMessage["message"]
        val uuid = remoteMessage["uuid"]

        return when(val eventType = remoteMessage[EVENT_TYPE]){
            TRACK -> NotificationData(title!!,message!!,uuid!!,eventType)
            ALBUM -> NotificationData(title!!,message!!,uuid!!,eventType)
            else -> {
                NotificationData("Yeniliklere Göz at","Yeniliklere göz atmak için uygulamayı ziyaret et","","")
            }
        }
    }
}