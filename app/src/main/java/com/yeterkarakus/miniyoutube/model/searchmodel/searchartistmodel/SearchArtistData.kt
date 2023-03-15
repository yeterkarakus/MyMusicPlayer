package com.yeterkarakus.miniyoutube.model.searchmodel.searchartistmodel

import com.yeterkarakus.miniyoutube.model.imagemodel.CoverArt

data class SearchArtistData (
    val uri: String,
    val profile: SearchArtistProfile,
    val visuals: SearchArtistImage
)
