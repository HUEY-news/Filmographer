package com.houston.filmographer.data

import com.houston.filmographer.domain.Movie

class MovieResponse(
    val searchType: String,
    val expression: String,
    val results: List<Movie>
)