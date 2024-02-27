package com.houston.filmographer.presentation

sealed interface ToastState {
    object None: ToastState
    data class Show(val message: String): ToastState
}