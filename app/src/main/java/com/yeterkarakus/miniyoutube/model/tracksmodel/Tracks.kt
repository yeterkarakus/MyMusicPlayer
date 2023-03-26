package com.yeterkarakus.miniyoutube.model.tracksmodel

data class Tracks(
    val album: TracksAlbum,
    val artists: List<TracksArtist>,
    val disc_number: Long,
    val duration_ms: Long,
    val explicit: Boolean,
    val external_ids: ExternalIDS,
    val external_urls: ExternalUrls,
    val id: String,
    val is_local: Boolean,
    val is_playable: Boolean,
    val name: String,
    val popularity: Long,
    val preview_url: String,
    val track_number: Long,
    val type: String,
    val uri: String


    )
