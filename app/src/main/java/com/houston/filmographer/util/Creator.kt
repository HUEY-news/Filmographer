package com.houston.filmographer.util

import android.content.Context
import com.houston.filmographer.data.impl.LocalStorage
import com.houston.filmographer.data.impl.MovieRepositoryImpl
import com.houston.filmographer.data.network.RetrofitNetworkClient
import com.houston.filmographer.domain.api.MovieInteractor
import com.houston.filmographer.domain.impl.MovieInteractorImpl
import com.houston.filmographer.domain.repository.MovieRepository
import com.houston.filmographer.presentation.poster.PosterPresenter
import com.houston.filmographer.presentation.poster.PosterView

object Creator {

    fun provideMovieInteractor(context: Context): MovieInteractor = MovieInteractorImpl(getMovieRepository(context))
    private fun getMovieRepository(context: Context): MovieRepository {
        return MovieRepositoryImpl(
            RetrofitNetworkClient(context),
            LocalStorage(context.getSharedPreferences("local_storage", Context.MODE_PRIVATE))
        )
    }

    fun providePosterPresenter(view: PosterView, image: String): PosterPresenter {
        return PosterPresenter(view, image)
    }
}