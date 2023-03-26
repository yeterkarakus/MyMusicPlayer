package com.yeterkarakus.miniyoutube.model.tracksmodel



data class TracksAlbum(
    val album_type: String,
    val artists: List<TracksArtist>,
    val external_urls: ExternalUrls,
    val id: String,
    val images: List<TrackImg>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val total_tracks: Long,
    val type: String,
    val uri: String
)
