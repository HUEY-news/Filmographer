package com.houston.filmographer.presentation

import com.houston.filmographer.domain.Movie

interface MovieView {

    fun showProgressBar(isVisible: Boolean)
    fun showMainMessage(isVisible: Boolean)
    fun showAdditionalMessage(text: String)

    fun setMainMessage(text: String)
    fun setContent(data: List<Movie>)
}