package com.houston.filmographer.data.network

import com.houston.filmographer.data.dto.MovieRequest
import com.houston.filmographer.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient: NetworkClient {

    private val baseUrl = "https://tv-api.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service = retrofit.create(TVapi::class.java)

    override fun doRequest(dto: Any): Response {
        if (dto is MovieRequest) {
            val response = service.searchMovie(dto.key, dto.expression).execute()
            val body = response.body() ?: Response()
            return body.apply { resultCode = response.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}