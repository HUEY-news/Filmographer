package com.houston.filmographer.presentation.history

import com.houston.filmographer.domain.search.model.Movie

sealed interface HistoryState {
    object Loading: HistoryState
    data class Content(val data: List<Movie>): HistoryState
    data class Empty(val message: String): HistoryState
}