package com.houston.filmographer.presentation

import com.houston.filmographer.domain.model.MovieCast

sealed interface CastState {
    object Loading: CastState
    data class Content(val data: MovieCast): CastState
    data class Error(val message: String): CastState
}