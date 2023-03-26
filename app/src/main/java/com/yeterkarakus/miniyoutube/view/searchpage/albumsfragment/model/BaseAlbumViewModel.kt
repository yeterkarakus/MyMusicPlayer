package com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model

import android.os.Parcel
import android.os.Parcelable

data class BaseAlbumViewModel(
    var albumDetailsList: List<AlbumDetailsViewModel>? = null,
    var albumTracksList: List<AlbumTracksViewModel>? = null

        ):Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("albumDetailsList"),
        TODO("albumTracksList")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseAlbumViewModel> {
        override fun createFromParcel(parcel: Parcel): BaseAlbumViewModel {
            return BaseAlbumViewModel(parcel)
        }

        override fun newArray(size: Int): Array<BaseAlbumViewModel?> {
            return arrayOfNulls(size)
        }
    }
}