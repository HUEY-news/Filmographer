package com.houston.filmographer.presentation.details

import com.houston.filmographer.domain.search.model.MovieDetails

sealed interface AboutState {
    data class Content(val movieDetails: MovieDetails): AboutState
    data class Error(val message: String): AboutState
}