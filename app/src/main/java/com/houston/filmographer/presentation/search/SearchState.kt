package com.houston.filmographer.presentation.search

import com.houston.filmographer.domain.model.Movie

sealed interface SearchState {
    object Loading: SearchState
    data class Content(val data: List<Movie>): SearchState
    data class Error(val message: String): SearchState
    data class Empty(val message: String): SearchState
}