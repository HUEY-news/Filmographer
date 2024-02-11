package com.houston.filmographer.domain

interface MovieRepository {
    fun searchMovie(
        key: String,
        expression: String
    ): List<Movie>
}