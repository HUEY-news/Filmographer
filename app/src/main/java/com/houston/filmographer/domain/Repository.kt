package com.houston.filmographer.domain

import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.domain.model.MovieCast
import com.houston.filmographer.domain.model.MovieDetails
import com.houston.filmographer.domain.model.Person
import com.houston.filmographer.util.Resource

interface Repository {

    fun searchMovie(key: String, expression: String): Resource<List<Movie>>
    fun searchName(key: String, expression: String): Resource<List<Person>>
    fun getMovieDetails(key: String, movieId: String): Resource<MovieDetails>
    fun getMovieCast(key: String, movieId: String): Resource<MovieCast>

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)

}