package com.houston.filmographer.domain.impl

import com.houston.filmographer.domain.api.MovieInteractor
import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.domain.repository.MovieRepository
import com.houston.filmographer.util.Resource
import kotlin.concurrent.thread

class MovieInteractorImpl(
    private val repository: MovieRepository
) : MovieInteractor {

    override fun searchMovie(key: String, expression: String, consumer: MovieInteractor.MovieConsumer) {
        Thread {
            val resource = repository.searchMovie(key, expression)
            when (resource) {
                is Resource.Success -> consumer.consume(resource.data, null)
                is Resource.Error -> consumer.consume(null, resource.message)

            }
        }.start()
    }

    override fun getMovieDetails(key: String, movieId: String, consumer: MovieInteractor.MovieDetailsConsumer) {
        Thread {
            val resource = repository.getMovieDetails(key, movieId)
            when (resource) {
                is Resource.Success -> consumer.consume(resource.data, null)
                is Resource.Error -> consumer.consume(null, resource.message)
            }
        }.start()
    }

    override fun addMovieToFavorites(movie: Movie) { repository.addMovieToFavorites(movie) }
    override fun removeMovieFromFavorites(movie: Movie) { repository.removeMovieFromFavorites(movie) }
}