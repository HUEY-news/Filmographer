package com.houston.filmographer.data.dto.name

import com.houston.filmographer.data.dto.Response

class NameSearchResponse(
    val searchType: String,
    val expression: String,
    val results: List<PersonDto>
) : Response()