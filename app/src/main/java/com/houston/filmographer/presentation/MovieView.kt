package com.houston.filmographer.presentation

import com.houston.filmographer.ui.MovieState

interface MovieView {

    fun render(state: MovieState)
    fun showToast(message: String)

}