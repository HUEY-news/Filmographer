package com.houston.filmographer.domain

import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.domain.model.MovieCast
import com.houston.filmographer.domain.model.MovieDetails
import com.houston.filmographer.domain.model.Person
import kotlinx.coroutines.flow.Flow

interface Interactor {

    fun searchMovie(key: String, expression: String): Flow<Pair<List<Movie>?, String?>>
    fun searchName(key: String, expression: String): Flow<Pair<List<Person>?, String?>>
    fun getMovieDetails(key: String, movieId: String): Flow<Pair<MovieDetails?, String?>>
    fun getMovieCast(key: String, movieId: String): Flow<Pair<MovieCast?, String?>>

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}