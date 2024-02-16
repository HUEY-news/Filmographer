package com.houston.filmographer.presentation.movie.view_model

sealed interface ToastState {
    object None: ToastState
    data class Show(val message: String): ToastState
}