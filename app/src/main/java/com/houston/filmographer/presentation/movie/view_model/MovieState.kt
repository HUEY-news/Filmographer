package com.houston.filmographer.presentation.movie.view_model

import com.houston.filmographer.domain.model.Movie

sealed interface MovieState {
    object Loading: MovieState
    data class Content(val data: List<Movie>): MovieState
    data class Error(val message: String): MovieState
    data class Empty(val message: String): MovieState
}