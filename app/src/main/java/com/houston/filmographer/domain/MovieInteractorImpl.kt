package com.houston.filmographer.domain

class MovieInteractorImpl(
    private val repository: MovieRepository
): MovieInteractor {

    override fun searchMovie(
        key: String,
        expression: String,
        consumer: MovieInteractor.MovieConsumer
    ) {
        Thread { consumer.consume(repository.searchMovie(key, expression)) }.start()
    }
}