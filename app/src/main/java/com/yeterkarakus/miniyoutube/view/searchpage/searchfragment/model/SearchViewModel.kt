package com.yeterkarakus.miniyoutube.view.searchpage.searchfragment.model

import android.os.Parcel
import android.os.Parcelable


data class SearchViewModel (
    var searchText : String? = null,
    var resultCount : Long? = 0,
    var albumRecordCount : Long? = 0,
    var trackRecordCount : Long? = 0,
    var albumList : List<AlbumViewModel>? = null,
    var trackList : List<TrackViewModel>? = null
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readValue(Long::class.java.classLoader) as? Long,
        TODO("albumList"),
        TODO("trackList")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(searchText)
        parcel.writeValue(resultCount)
        parcel.writeValue(albumRecordCount)
        parcel.writeValue(trackRecordCount)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SearchViewModel> {
        override fun createFromParcel(parcel: Parcel): SearchViewModel {
            return SearchViewModel(parcel)
        }

        override fun newArray(size: Int): Array<SearchViewModel?> {
            return arrayOfNulls(size)
        }
    }
}