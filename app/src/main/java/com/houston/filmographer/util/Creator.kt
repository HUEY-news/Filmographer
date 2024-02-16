package com.houston.filmographer.util

import android.app.Application
import android.content.Context
import com.houston.filmographer.data.impl.MovieRepositoryImpl
import com.houston.filmographer.data.network.NetworkClient
import com.houston.filmographer.data.network.RetrofitNetworkClient
import com.houston.filmographer.domain.api.MovieInteractor
import com.houston.filmographer.domain.impl.MovieInteractorImpl
import com.houston.filmographer.domain.repository.MovieRepository
import com.houston.filmographer.presentation.movie.view_model.MovieViewModel
import com.houston.filmographer.presentation.poster.PosterPresenter
import com.houston.filmographer.presentation.poster.PosterView

object Creator {

    fun provideMovieInteractor(context: Context): MovieInteractor = MovieInteractorImpl(
        getMovieRepository(context)
    )
    private fun getMovieRepository(context: Context): MovieRepository = MovieRepositoryImpl(
        getNetworkClient(context)
    )
    private fun getNetworkClient(context: Context): NetworkClient = RetrofitNetworkClient(context)

    fun provideMovieViewModel(application: Application): MovieViewModel {
        return MovieViewModel(application)
    }

    fun providePosterPresenter(view: PosterView, image: String): PosterPresenter {
        return PosterPresenter(view, image)
    }
}