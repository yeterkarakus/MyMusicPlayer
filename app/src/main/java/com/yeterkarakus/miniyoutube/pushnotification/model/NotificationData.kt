package com.yeterkarakus.miniyoutube.pushnotification.model

import androidx.annotation.DrawableRes
import com.yeterkarakus.miniyoutube.R


data class NotificationData (
    val title : String,
    val message : String,
    val uuid : String,
    val eventType : String,
    @DrawableRes val notificationIcon : Int = R.drawable.notification_icon
)
