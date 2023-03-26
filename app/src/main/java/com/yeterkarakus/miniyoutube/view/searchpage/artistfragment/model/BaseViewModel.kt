package com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model

import android.os.Parcel
import android.os.Parcelable

data class BaseViewModel(
    val artistList: List<ArtistViewModel>,
    val artistOverviewList: List<ArtistOverViewModel>,
    val albumsList : List<AlbumsViewModel>
):Parcelable {
    constructor(parcel: Parcel) : this(
        TODO("artistList"),
        TODO("artistOverviewList"),
        TODO("albumsList")
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BaseViewModel> {
        override fun createFromParcel(parcel: Parcel): BaseViewModel {
            return BaseViewModel(parcel)
        }

        override fun newArray(size: Int): Array<BaseViewModel?> {
            return arrayOfNulls(size)
        }
    }
}
