package com.yeterkarakus.miniyoutube.model.artistmodel.artist

import com.yeterkarakus.miniyoutube.model.albummodel.albums.ExternalUrls
import com.yeterkarakus.miniyoutube.model.albummodel.albums.Images


data class Artist(
    val external_urls: ExternalUrls,
    val followers: Followers,
    val id: String,
    val images: List<Images>,
    val name: String,
    val popularity: Long,
    val type: String,
    val uri: String
)