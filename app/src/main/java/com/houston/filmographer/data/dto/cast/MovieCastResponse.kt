package com.houston.filmographer.data.dto.cast

import com.houston.filmographer.data.dto.Response

data class MovieCastResponse(
    val actors: List<ActorDto>,
    val directors: DirectorsDto,
    val errorMessage: String,
    val fullTitle: String,
    val imDbId: String,
    val others: List<OtherDto>,
    val title: String,
    val type: String,
    val writers: WritersDto,
    val year: String
): Response()