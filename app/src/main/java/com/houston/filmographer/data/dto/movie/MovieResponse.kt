package com.houston.filmographer.data.dto.movie

import com.houston.filmographer.data.dto.Response

class MovieResponse(
    val searchType: String,
    val expression: String,
    val results: List<MovieDto>
): Response()