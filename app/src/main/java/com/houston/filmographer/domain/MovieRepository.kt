package com.houston.filmographer.domain

import com.houston.filmographer.util.Resource

interface MovieRepository {
    fun searchMovie(
        key: String,
        expression: String
    ): Resource<List<Movie>>
}