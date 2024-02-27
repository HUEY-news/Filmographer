package com.houston.filmographer.di

import com.houston.filmographer.presentation.cast.MovieCastViewModel
import com.houston.filmographer.presentation.search.SearchViewModel
import com.houston.filmographer.presentation.details.AboutViewModel
import com.houston.filmographer.presentation.details.PosterViewModel
import com.houston.filmographer.presentation.name.NameViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(interactor = get()) }
    viewModel { NameViewModel(interactor = get()) }
    viewModel { (movieId: String) -> AboutViewModel(movieId = movieId, interactor = get()) }
    viewModel { (posterUrl: String) -> PosterViewModel(posterUrl = posterUrl) }
    viewModel { (movieId: String) -> MovieCastViewModel(movieId = movieId, interactor = get()) }
}