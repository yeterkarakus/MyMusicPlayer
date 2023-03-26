package com.yeterkarakus.miniyoutube.api


import com.yeterkarakus.miniyoutube.model.albummodel.albums.AlbumsContainer
import com.yeterkarakus.miniyoutube.model.albummodel.albumtracks.AlbumTracksContainer
import com.yeterkarakus.miniyoutube.model.artistmodel.artist.ArtistContainer
import com.yeterkarakus.miniyoutube.model.artistmodel.artistoverview.ArtistOverviewContainer
import com.yeterkarakus.miniyoutube.model.searchmodel.SearchContainer
import com.yeterkarakus.miniyoutube.model.searchmodel.SearchType
import com.yeterkarakus.miniyoutube.model.tracksmodel.TracksContainer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface RetrofitApi {

    @Headers(
        "X-RapidAPI-Key: 7195876106msh2746fdb5fe5647bp1941d8jsn5e4d7ef6a79e",
        "X-RapidAPI-Host: spotify23.p.rapidapi.com"
    )
    @GET("artists/")
    suspend fun getArtist(@Query("ids") ids: String) : Response<ArtistContainer>
    @Headers(
        "X-RapidAPI-Key: 7195876106msh2746fdb5fe5647bp1941d8jsn5e4d7ef6a79e",
        "X-RapidAPI-Host: spotify23.p.rapidapi.com"
    )
    @GET("artist_overview/")
    suspend fun getArtistOverview(@Query("id") id: String) : Response<ArtistOverviewContainer>

    @Headers(
        "X-RapidAPI-Key: 7195876106msh2746fdb5fe5647bp1941d8jsn5e4d7ef6a79e",
        "X-RapidAPI-Host: spotify23.p.rapidapi.com"
    )
    @GET("search/")
    suspend fun search(@Query("q") q: String,
                       @Query("type") type: SearchType,
                       @Query("offset") offset: Int = 0,
                       @Query("limit") limit : Int = 3) : Response<SearchContainer>



    @Headers(
        "X-RapidAPI-Key: 7195876106msh2746fdb5fe5647bp1941d8jsn5e4d7ef6a79e",
        "X-RapidAPI-Host: spotify23.p.rapidapi.com"
    )
    @GET("albums/")
    suspend fun getAlbums(@Query("ids") ids : String): Response<AlbumsContainer>
    @Headers(
        "X-RapidAPI-Key: 7195876106msh2746fdb5fe5647bp1941d8jsn5e4d7ef6a79e",
        "X-RapidAPI-Host: spotify23.p.rapidapi.com"
    )
    @GET("tracks/")
    suspend fun getTrack(@Query("ids") ids : String): Response<TracksContainer>
    @Headers(
        "X-RapidAPI-Key: 7195876106msh2746fdb5fe5647bp1941d8jsn5e4d7ef6a79e",
        "X-RapidAPI-Host: spotify23.p.rapidapi.com"
    )
    @GET("album_tracks/")
    suspend fun getAlbumTracks(@Query("id") id : String,
                               @Query("offset") offset: Int = 0,
                               @Query("limit") limit : Int = 10) : Response<AlbumTracksContainer>

}

