package com.houston.filmographer.data.creator

import android.app.Activity
import com.houston.filmographer.data.MovieRepositoryImpl
import com.houston.filmographer.data.network.NetworkClient
import com.houston.filmographer.data.network.RetrofitNetworkClient
import com.houston.filmographer.domain.MovieInteractor
import com.houston.filmographer.domain.MovieInteractorImpl
import com.houston.filmographer.domain.MovieRepository
import com.houston.filmographer.ui.MovieAdapter
import com.houston.filmographer.presentation.MovieController
import com.houston.filmographer.presentation.PosterController

object Creator {

    fun provideMovieInteractor(): MovieInteractor = MovieInteractorImpl(getMovieRepository())
    private fun getMovieRepository(): MovieRepository = MovieRepositoryImpl(getNetworkClient())
    private fun getNetworkClient(): NetworkClient = RetrofitNetworkClient()

    fun provideMovieController(activity: Activity, adapter: MovieAdapter): MovieController {
        return MovieController(activity, adapter)
    }

    fun providePosterController(activity: Activity): PosterController {
        return PosterController(activity)
    }

}