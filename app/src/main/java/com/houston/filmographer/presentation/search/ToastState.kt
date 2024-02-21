package com.houston.filmographer.presentation.search

sealed interface ToastState {
    object None: ToastState
    data class Show(val message: String): ToastState
}