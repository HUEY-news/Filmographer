package com.houston.filmographer.presentation.cast

import com.houston.filmographer.domain.model.MovieCast

sealed interface CastState {
    object Loading: CastState
    data class Content(val title: String, val items: List<CastItem>): CastState
    data class Error(val message: String): CastState
}