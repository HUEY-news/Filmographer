package com.houston.filmographer.util

import android.app.Application
import com.houston.filmographer.presentation.MoviePresenter

class App: Application() {
    var presenter: MoviePresenter? = null

}