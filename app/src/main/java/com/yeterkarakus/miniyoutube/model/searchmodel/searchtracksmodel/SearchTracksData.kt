package com.yeterkarakus.miniyoutube.model.searchmodel.searchtracksmodel

import com.yeterkarakus.miniyoutube.model.searchmodel.searchalbumsmodel.SearchAlbumDataArtist

data class SearchTracksData (
    val uri: String,
    val id: String,
    val name: String,
    val albumOfTrack: AlbumOfTrack,
    val artists: SearchAlbumDataArtist
)