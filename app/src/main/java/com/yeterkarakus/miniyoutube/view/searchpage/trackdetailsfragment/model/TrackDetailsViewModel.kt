package com.yeterkarakus.miniyoutube.view.searchpage.trackdetailsfragment.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.io.Serializable
@Parcelize
data class TrackDetailsViewModel(
    var tracksImgUrl : String? = null,
    var id: String? = null,
    var name: String? = null,
    var artistId: String? = null,
    var artistName: String? = null,
    var albumImgUrl : String? = null,
    var albumName : String? = null,
    var albumId :String? = null) : Parcelable

