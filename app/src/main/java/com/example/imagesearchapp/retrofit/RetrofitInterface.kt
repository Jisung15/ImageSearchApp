package com.example.imagesearchapp.retrofit

import com.example.imagesearchapp.dataclass.ImageData
import com.example.imagesearchapp.dataclass.VideoData
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("v2/search/image")
    suspend fun getImage(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): ImageData

    @GET("v2/search/vclip")
    suspend fun getVideo(
        @Header("Authorization") apiKey: String,
        @Query("query") query: String,
        @Query("sort") sort: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): VideoData
}