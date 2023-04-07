package com.yeterkarakus.miniyoutube.pushnotification.model



data class NotificationData (
    val title : String,
    val message : String,
    val uuid : String,
    val eventType : String
)
