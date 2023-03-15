package com.yeterkarakus.miniyoutube.dependencyinjection

import android.content.Context
import com.bumptech.glide.Glide
import com.yeterkarakus.miniyoutube.api.RetrofitApi

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

@Provides
    fun injectRetrofit(): RetrofitApi{
         return Retrofit.Builder()
             .baseUrl("https://spotify23.p.rapidapi.com")
             .addConverterFactory(GsonConverterFactory.create())
             .build()
             .create(RetrofitApi::class.java)
}
    fun injectGlide(@ApplicationContext context:Context) = Glide.with(context)

}