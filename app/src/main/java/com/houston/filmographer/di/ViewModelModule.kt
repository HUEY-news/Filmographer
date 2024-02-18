package com.houston.filmographer.di

import com.houston.filmographer.presentation.movie.view_model.MovieViewModel
import com.houston.filmographer.presentation.details.view_model.AboutViewModel
import com.houston.filmographer.presentation.details.view_model.PosterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(interactor = get()) }
    viewModel { (movieId: String) -> AboutViewModel(movieId = movieId, interactor = get()) }
    viewModel { (posterUrl: String) -> PosterViewModel(posterUrl = posterUrl) }
}