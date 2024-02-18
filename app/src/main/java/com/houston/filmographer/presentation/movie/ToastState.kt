package com.houston.filmographer.presentation.movie

sealed interface ToastState {
    object None: ToastState
    data class Show(val message: String): ToastState
}