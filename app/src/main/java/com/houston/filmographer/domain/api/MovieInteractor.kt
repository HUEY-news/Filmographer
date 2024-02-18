package com.houston.filmographer.domain.api

import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.domain.model.MovieDetails

interface MovieInteractor {

    fun searchMovie(key: String, expression: String, consumer: MovieConsumer)
    interface MovieConsumer { fun consume(data: List<Movie>?, message: String?) }
    fun getMovieDetails(key: String, movieId: String, consumer: MovieDetailsConsumer)
    interface MovieDetailsConsumer { fun consume (data: MovieDetails?, message: String?) }

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}