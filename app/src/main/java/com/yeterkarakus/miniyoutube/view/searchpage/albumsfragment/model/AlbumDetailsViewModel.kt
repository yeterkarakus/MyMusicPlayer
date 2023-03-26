package com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize



data class AlbumDetailsViewModel(
    var id: String,
    var imageUrl: String,
    var name: String,
    var uri: String,
    var artisId: String,
    var artistName: String,
    var artistUri: String
)