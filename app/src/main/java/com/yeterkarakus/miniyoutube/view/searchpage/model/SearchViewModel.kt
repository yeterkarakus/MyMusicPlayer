package com.yeterkarakus.miniyoutube.view.searchpage.model


import java.io.Serializable


data class SearchViewModel (
    var searchText : String? = null,
    var resultCount : Long? = 0,
    var albumRecordCount : Long? = 0,
    var trackRecordCount : Long? = 0,
    var albumList : List<AlbumViewModel>? = null,
    var trackList : List<TrackViewModel>? = null
): Serializable