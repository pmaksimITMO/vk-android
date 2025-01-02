package com.example.apireader.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object HttpService {
    private const val BASE_URL = "https://api.giphy.com/"

    private val service = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val giphyApi: GiphyApi = service.create(GiphyApi::class.java)
}