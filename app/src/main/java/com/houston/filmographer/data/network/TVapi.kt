package com.houston.filmographer.data.network

import com.houston.filmographer.data.dto.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TVapi{

    @GET("/en/API/SearchMovie/{key}/{expression}")
    fun searchMovie(
        @Path("key") key: String,
        @Path("expression") expression: String
    ): Call<MovieResponse>
}