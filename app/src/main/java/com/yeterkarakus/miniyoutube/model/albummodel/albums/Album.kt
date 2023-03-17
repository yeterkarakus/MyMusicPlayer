package com.yeterkarakus.miniyoutube.model.albummodel.albums

import javax.xml.transform.Source

data class Album(
    val album_type: String,
    val artists: List<AlbumArtist>,
    val external_ids: ExternalIDS,
    val external_urls: ExternalUrls,
    val genres: List<Any?>,
    val id: String,
    val images: List<Images>,
    val label: String,
    val name: String,
    val popularity: Long,
    val releaseDate: String,
    val release_date_precision: String,
    val total_tracks: Long,
    //val tracks: Tracks,
    val type: String,
    val uri: String
    )

