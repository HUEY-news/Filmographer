package com.houston.filmographer.ui

import com.houston.filmographer.domain.Movie

data class MovieState(
    val content: List<Movie>,
    val loading: Boolean,
    val error: String?
)
