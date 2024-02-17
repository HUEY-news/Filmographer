package com.houston.filmographer.util

import com.houston.filmographer.presentation.poster.PosterPresenter
import com.houston.filmographer.presentation.poster.PosterView

object Creator {

    fun providePosterPresenter(view: PosterView, image: String): PosterPresenter {
        return PosterPresenter(view, image)
    }
}