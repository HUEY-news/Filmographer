package com.houston.filmographer.presentation

import com.houston.filmographer.domain.Movie

interface MovieView {

    fun showLoading()
    fun showContent(data: List<Movie>)
    fun showError(message: String)
    fun showEmpty(message: String)
    fun showToast(message: String)

}