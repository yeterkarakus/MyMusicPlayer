package com.yeterkarakus.miniyoutube

import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.AlbumDetailsViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.albumsfragment.model.AlbumTracksViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.AlbumsViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.ArtistOverViewModel
import com.yeterkarakus.miniyoutube.view.searchpage.artistfragment.model.ArtistViewModel
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
         trackDetailsViewModel.previewUrl=track[0].previewUrl

         return trackDetailsViewModel
     }

   fun getAlbumDetailsViewModel(album : MutableList<AlbumDetailsViewModel>)  :AlbumDetailsViewModel{

      val albumDetailsViewModel = AlbumDetailsViewModel()

      albumDetailsViewModel.id = album[0].id
      albumDetailsViewModel.imageUrl = album[0].imageUrl
      albumDetailsViewModel.uri=album[0].uri
      albumDetailsViewModel.name = album[0].name
      albumDetailsViewModel.artistName = album[0].artistName
      albumDetailsViewModel.artistUri = album[0].artistUri

      return  albumDetailsViewModel
   }

   fun getAlbumTracksViewModel (albumTracks : MutableList<AlbumTracksViewModel>) : AlbumTracksViewModel {
      val albumTracksViewModel = AlbumTracksViewModel()
      albumTracksViewModel.trackNumber = albumTracks[0].trackNumber
      albumTracksViewModel.uri = albumTracks[0].uri
      albumTracksViewModel.artistName = albumTracks[0].artistName
      albumTracksViewModel.name = albumTracks[0].name

      return albumTracksViewModel
   }

   fun getArtistOverViewModel(artistOver : MutableList<ArtistOverViewModel>) : ArtistOverViewModel{

      val artistOverViewModel = ArtistOverViewModel()
      artistOverViewModel.id = artistOver[0].id
      artistOverViewModel.uri=artistOver[0].uri
      artistOverViewModel.bio = artistOver[0].bio

      return  artistOverViewModel
   }

   fun getAlbumsViewModel(albumsList : MutableList<AlbumsViewModel>) : AlbumsViewModel{

      val albumsViewModel = AlbumsViewModel()
      albumsViewModel.albumsId  = albumsList[0].albumsId
      albumsViewModel.albumsName  = albumsList[0].albumsName
      albumsViewModel.albumsDate  = albumsList[0].albumsDate
      albumsViewModel.albumsImgUrl = albumsList[0].albumsImgUrl

      return  albumsViewModel
   }

   fun getArtistViewModel (artistList: MutableList<ArtistViewModel>): ArtistViewModel{

      val artistViewModel = ArtistViewModel()
      artistViewModel.id = artistList[0].id
      artistViewModel.name = artistList[0].name
      artistViewModel.uri = artistList[0].uri
      artistViewModel.imageUrl = artistList[0].imageUrl

      return artistViewModel
   }


}