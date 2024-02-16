package com.houston.filmographer.domain.api

import com.houston.filmographer.domain.model.Movie

interface MovieInteractor {

    fun searchMovie(key: String, expression: String, consumer: MovieConsumer)

    interface MovieConsumer {
        fun consume(data: List<Movie>?, message: String?)
    }
}