package com.houston.filmographer.domain

import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.domain.model.MovieCast
import com.houston.filmographer.domain.model.MovieDetails
import com.houston.filmographer.domain.model.Person

interface Interactor {

    fun searchMovie(key: String, expression: String, consumer: MovieSearchConsumer)
    interface MovieSearchConsumer { fun consume(data: List<Movie>?, message: String?) }
    fun searchName(key: String, expression: String, consumer: NameSearchConsumer)
    interface NameSearchConsumer { fun consume(data: List<Person>?, message: String?) }
    fun getMovieDetails(key: String, movieId: String, consumer: MovieDetailsConsumer)
    interface MovieDetailsConsumer { fun consume(data: MovieDetails?, message: String?) }
    fun getMovieCast(key: String, movieId: String, consumer: MovieCastConsumer)
    interface MovieCastConsumer { fun consume(data: MovieCast?, message: String?) }

    fun addMovieToFavorites(movie: Movie)
    fun removeMovieFromFavorites(movie: Movie)
}