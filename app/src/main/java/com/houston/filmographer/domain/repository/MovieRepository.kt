package com.houston.filmographer.domain.repository

import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.util.Resource

interface MovieRepository {

    fun searchMovie(key: String, expression: String): Resource<List<Movie>>
    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)

}