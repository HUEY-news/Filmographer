package com.houston.filmographer.data.dto

class MovieResponse(
    val searchType: String,
    val expression: String,
    val results: List<MovieDto>
): Response()