package com.houston.filmographer.domain

import com.houston.filmographer.util.Resource

class MovieInteractorImpl(
    private val repository: MovieRepository
) : MovieInteractor {

    override fun searchMovie(
        key: String,
        expression: String,
        consumer: MovieInteractor.MovieConsumer
    ) {
        Thread {
            val resource = repository.searchMovie(key, expression)
            when (resource) {
                is Resource.Success -> consumer.consume(resource.data, null)
                is Resource.Error -> consumer.consume(null, resource.message)

            }
        }.start()
    }
}