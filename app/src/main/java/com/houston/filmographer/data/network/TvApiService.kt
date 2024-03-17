package com.houston.filmographer.data.network

import com.houston.filmographer.data.dto.cast.MovieCastResponse
import com.houston.filmographer.data.dto.details.MovieDetailsResponse
import com.houston.filmographer.data.dto.movie.MovieSearchResponse
import com.houston.filmographer.data.dto.name.NameSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TvApiService{

    @GET("/en/API/SearchMovie/{key}/{expression}")
    suspend fun searchMovie(
        @Path("key") key: String,
        @Path("expression") expression: String
    ): MovieSearchResponse

    @GET("/en/API/SearchName/{key}/{expression}")
    suspend fun searchName(
        @Path("key") key: String,
        @Path("expression") expression: String
    ): NameSearchResponse

    @GET("/en/API/Title/{key}/{movie_id}")
    suspend fun getMovieDetails(
        @Path("key") key: String,
        @Path("movie_id") movieId: String
    ): MovieDetailsResponse

    @GET("/en/API/FullCast/{key}/{movie_id}")
    suspend fun getMovieCast(
        @Path("key") key: String,
        @Path("movie_id") movieId: String
    ): MovieCastResponse
}