package com.houston.filmographer.data.creator

import android.app.Activity
import android.content.Context
import com.houston.filmographer.data.MovieRepositoryImpl
import com.houston.filmographer.data.network.NetworkClient
import com.houston.filmographer.data.network.RetrofitNetworkClient
import com.houston.filmographer.domain.MovieInteractor
import com.houston.filmographer.domain.MovieInteractorImpl
import com.houston.filmographer.domain.MovieRepository
import com.houston.filmographer.ui.MovieAdapter
import com.houston.filmographer.presentation.MoviePresenter
import com.houston.filmographer.presentation.MovieView
import com.houston.filmographer.presentation.PosterController

object Creator {

    fun provideMovieInteractor(context: Context): MovieInteractor = MovieInteractorImpl(getMovieRepository(context))
    private fun getMovieRepository(context: Context): MovieRepository = MovieRepositoryImpl(getNetworkClient(context))
    private fun getNetworkClient(context: Context): NetworkClient = RetrofitNetworkClient(context)

    fun provideMoviePresenter(view: MovieView, context: Context): MoviePresenter {
        return MoviePresenter(view, context)
    }

    fun providePosterController(activity: Activity): PosterController {
        return PosterController(activity)
    }
}