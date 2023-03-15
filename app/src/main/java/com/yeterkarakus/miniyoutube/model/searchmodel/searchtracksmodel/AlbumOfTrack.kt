package com.yeterkarakus.miniyoutube.model.searchmodel.searchtracksmodel

import com.yeterkarakus.miniyoutube.model.imagemodel.CoverArt

data class AlbumOfTrack (
        val uri: String,
        val name: String,
        val coverArt: CoverArt,
        val id: String
        )
