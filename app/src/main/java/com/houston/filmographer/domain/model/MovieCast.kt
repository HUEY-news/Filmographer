package com.houston.filmographer.domain.model

data class MovieCast(
    val imdbId: String,
    val fullTitle: String,
    val directors: List<Person>,
    val writers: List<Person>,
    val actors: List<Person>,
    val others: List<Person>
)