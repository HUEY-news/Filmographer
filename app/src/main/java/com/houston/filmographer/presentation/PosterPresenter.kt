package com.houston.filmographer.presentation

class PosterPresenter(
    private val view: PosterView,
    private val image: String
) {

    fun onCreate() {
        view.setImage(image)
    }
}