package com.houston.filmographer.ui

import com.houston.filmographer.domain.Movie

sealed interface MovieState {
    object Loading: MovieState
    data class Content(val data: List<Movie>): MovieState
    data class Error(val message: String): MovieState
    data class Empty(val message: String): MovieState
}