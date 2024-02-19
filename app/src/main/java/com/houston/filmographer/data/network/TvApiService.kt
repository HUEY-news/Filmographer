package com.houston.filmographer.data.network

import com.houston.filmographer.data.dto.cast.MovieCastResponse
import com.houston.filmographer.data.dto.details.MovieDetailsResponse
import com.houston.filmographer.data.dto.movie.MovieResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TvApiService{

    @GET("/en/API/SearchMovie/{key}/{expression}")
    fun searchMovie(
        @Path("key") key: String,
        @Path("expression") expression: String
    ): Call<MovieResponse>

    @GET("/en/API/Title/{key}/{movie_id}")
    fun getMovieDetails(
        @Path("key") key: String,
        @Path("movie_id") movieId: String
    ): Call<MovieDetailsResponse>

    @GET("/en/API/FullCast/{key}/{movie_id}")
    fun getMovieCast(
        @Path("key") key: String,
        @Path("movie_id") movieId: String
    ): Call<MovieCastResponse>
}