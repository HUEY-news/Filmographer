package com.houston.filmographer.data.creator

import com.houston.filmographer.data.MovieRepositoryImpl
import com.houston.filmographer.data.network.NetworkClient
import com.houston.filmographer.data.network.RetrofitNetworkClient
import com.houston.filmographer.domain.MovieInteractor
import com.houston.filmographer.domain.MovieInteractorImpl
import com.houston.filmographer.domain.MovieRepository

object Creator {

    fun provideMovieInteractor(): MovieInteractor = MovieInteractorImpl(getMovieRepository())
    private fun getMovieRepository(): MovieRepository = MovieRepositoryImpl(getNetworkClient())
    private fun getNetworkClient(): NetworkClient = RetrofitNetworkClient()

}