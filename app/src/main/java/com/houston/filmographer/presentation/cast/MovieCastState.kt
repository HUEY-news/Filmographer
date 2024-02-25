package com.houston.filmographer.presentation.cast

// TODO: - Реализовать библиотеку AdapterDelegates...
// TODO: - заменить val items: List<CastItem> на val items: List<ViewItem>

sealed interface MovieCastState {
    object Loading: MovieCastState
    data class Content(val title: String, val items: List<MovieCastItem>): MovieCastState
    data class Error(val message: String): MovieCastState
}