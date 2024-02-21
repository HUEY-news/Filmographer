package com.houston.filmographer.domain

import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.util.Resource

class InteractorImpl(
    private val repository: Repository
) : Interactor {

    override fun searchMovie(
        key: String,
        expression: String,
        consumer: Interactor.MovieConsumer
    ) {
        Thread {
            val resource = repository.searchMovie(key, expression)
            when (resource) {
                is Resource.Success -> consumer.consume(data = resource.data, message = null)
                is Resource.Error -> consumer.consume(data = null, message = resource.message)

            }
        }.start()
    }

    override fun getMovieDetails(
        key: String,
        movieId: String,
        consumer: Interactor.MovieDetailsConsumer
    ) {
        Thread {
            val resource = repository.getMovieDetails(key, movieId)
            when (resource) {
                is Resource.Success -> consumer.consume(data = resource.data, message = null)
                is Resource.Error -> consumer.consume(data = null, message = resource.message)
            }
        }.start()
    }

    override fun getMovieCast(
        key: String,
        movieId: String,
        consumer: Interactor.MovieCastConsumer
    ) {
        Thread {
            val resource = repository.getMovieCast(key, movieId)
            when (resource) {
                is Resource.Success -> consumer.consume(data = resource.data, message = null)
                is Resource.Error -> consumer.consume(data = null, message = resource.message)
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) { repository.addMovieToFavorites(movie) }
    override fun removeMovieFromFavorites(movie: Movie) { repository.removeMovieFromFavorites(movie) }
}