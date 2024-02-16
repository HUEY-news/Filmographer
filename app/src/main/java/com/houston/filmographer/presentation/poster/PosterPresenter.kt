package com.houston.filmographer.presentation.poster

class PosterPresenter(
    private val view: PosterView,
    private val image: String
) {

    fun onCreate() {
        view.setImage(image)
    }
}