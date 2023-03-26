package com.yeterkarakus.miniyoutube.model.artistmodel.artistoverview

data class ArtistOverview(
    val id: String,
    val uri: String,
    val profile: ArtistProfile,
    val visuals: ArtistVisuals,
    val discography: Discography,

)

