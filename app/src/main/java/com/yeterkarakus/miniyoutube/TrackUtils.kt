package com.yeterkarakus.miniyoutube

import com.yeterkarakus.miniyoutube.view.searchpage.trackdetailsfragment.model.TrackDetailsViewModel

class TrackUtils {

      fun getTracksDetailsViewModel( track : MutableList<TrackDetailsViewModel>) : TrackDetailsViewModel{


         val trackDetailsViewModel = TrackDetailsViewModel()

         trackDetailsViewModel.tracksImgUrl = track[0].tracksImgUrl
         trackDetailsViewModel.id=track[0].id
         trackDetailsViewModel.name= track[0].name
         trackDetailsViewModel.artistId= track[0].artistId
         trackDetailsViewModel.artistName=track[0].artistName
         trackDetailsViewModel.albumName=track[0].albumName
         trackDetailsViewModel.albumId=track[0].id
         trackDetailsViewModel.albumImgUrl=track[0].albumImgUrl


         return trackDetailsViewModel

     }
}