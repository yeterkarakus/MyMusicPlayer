package com.yeterkarakus.miniyoutube.model.artistmodel.artistoverview

import com.yeterkarakus.miniyoutube.model.imagemodel.CoverArt

data class Item (
    val id: String,
    val uri: String,
    val name: String,
    val date: Date,
    val coverArt: CoverArt,
)