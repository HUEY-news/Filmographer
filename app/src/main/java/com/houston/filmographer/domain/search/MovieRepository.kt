package com.houston.filmographer.domain.search

import com.houston.filmographer.domain.search.model.Movie
import com.houston.filmographer.domain.search.model.MovieCast
import com.houston.filmographer.domain.search.model.MovieDetails
import com.houston.filmographer.domain.search.model.Person
import com.houston.filmographer.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {

    fun searchMovie(key: String, expression: String): Flow<Resource<List<Movie>>>
    fun searchName(key: String, expression: String): Flow<Resource<List<Person>>>
    fun getMovieDetails(key: String, movieId: String): Flow<Resource<MovieDetails>>
    fun getMovieCast(key: String, movieId: String): Flow<Resource<MovieCast>>

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)

}