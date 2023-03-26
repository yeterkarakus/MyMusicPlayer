package com.yeterkarakus.miniyoutube.model.artistmodel.artistoverview

data class Discography (
    val popularReleases: ArtistOverviewAlbum,
    val albums: ArtistOverviewAlbum,
    val topTracks: TopTracks
)