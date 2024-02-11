package com.houston.filmographer.domain

interface MovieInteractor {

    fun searchMovie(key: String, expression: String, consumer: MovieConsumer)

    interface MovieConsumer {
        fun consume(movies: List<Movie>)
    }
}