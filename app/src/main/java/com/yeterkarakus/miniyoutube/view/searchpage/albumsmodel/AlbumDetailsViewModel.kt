package com.yeterkarakus.miniyoutube.view.searchpage.albumsmodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class AlbumDetailsViewModel(
    var id : String,
    var imageUrl: String,
    var name: String,
    var uri: String,
    var artisId: String,
    val artistName: String,
    var artistUri: String
): Parcelable