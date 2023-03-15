package com.yeterkarakus.miniyoutube.api


import com.yeterkarakus.miniyoutube.model.artistmodel.ArtistContainer
import com.yeterkarakus.miniyoutube.model.searchmodel.SearchContainer
import com.yeterkarakus.miniyoutube.model.searchmodel.SearchType
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface RetrofitApi {

    @Headers(
        "X-RapidAPI-Key: acf5e91f6dmsh0b7dbb482122850p1b594ajsna5f72270471f",
        "X-RapidAPI-Host: spotify23.p.rapidapi.com"
    )
    @GET("artists/")
    suspend fun getArtist(@Query("ids") ids: String) : Response<ArtistContainer>

    @Headers(
        "X-RapidAPI-Key: acf5e91f6dmsh0b7dbb482122850p1b594ajsna5f72270471f",
        "X-RapidAPI-Host: spotify23.p.rapidapi.com"
    )
    @GET("search/")
    suspend fun search(@Query("q") q: String,
                       @Query("type") type: SearchType,
                       @Query("offset") offset: Int = 0,
                       @Query("limit") limit : Int = 3) : Response<SearchContainer>
}