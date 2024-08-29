package com.example.imagesearchapp

import com.example.imagesearchapp.dataClass.ImageData
import com.example.imagesearchapp.dataClass.VideoData
import com.example.imagesearchapp.retrofit.RetrofitClient

class SearchRepository {

    suspend fun getImage(query : String) : ImageData {
        return RetrofitClient.makeRetrofit.getImage(
            apiKey = BuildConfig.KAKAO_API_KEY,
            query = query,
            size = 50
        )
    }

    suspend fun getVideo(query : String) : VideoData {
        return RetrofitClient.makeRetrofit.getVideo(
            apiKey = BuildConfig.KAKAO_API_KEY,
            query = query,
            size = 30
        )
    }
}
