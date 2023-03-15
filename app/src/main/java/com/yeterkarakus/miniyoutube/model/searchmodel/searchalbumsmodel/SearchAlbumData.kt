package com.yeterkarakus.miniyoutube.model.searchmodel.searchalbumsmodel

import com.yeterkarakus.miniyoutube.model.imagemodel.CoverArt

data class SearchAlbumData (
    val uri: String,
    val name: String,
    val artists: SearchAlbumDataArtist,
    val coverArt: CoverArt,
    val date: SearchAlbumDate
)
