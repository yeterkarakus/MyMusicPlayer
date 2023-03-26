package com.yeterkarakus.miniyoutube.model.searchmodel

import com.yeterkarakus.miniyoutube.model.searchmodel.searchalbumsmodel.SearchAlbums
import com.yeterkarakus.miniyoutube.model.searchmodel.searchartistmodel.SearchArtists
import com.yeterkarakus.miniyoutube.model.searchmodel.searchtracksmodel.SearchTracks

data class SearchContainer(
    val message: String,
    val tracks: SearchTracks,
    val albums: SearchAlbums,
    val artists :SearchArtists )
