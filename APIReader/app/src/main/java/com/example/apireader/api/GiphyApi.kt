package com.example.apireader.api

import com.example.apireader.api.dto.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyApi {
    @GET("v1/gifs/trending")
    suspend fun getTrendyGifsByRating(
        @Query("api_key") apiKey: String,
        @Query("rating") rating: String = "g",
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0
    ) : GiphyResponse
}