package com.houston.filmographer.presentation.cast

// TODO: - Реализовать библиотеку AdapterDelegates...
// TODO: - заменить val items: List<CastItem> на val items: List<ViewItem>

sealed interface CastState {
    object Loading: CastState
    data class Content(val title: String, val items: List<CastItem>): CastState
    data class Error(val message: String): CastState
}