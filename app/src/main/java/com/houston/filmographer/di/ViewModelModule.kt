package com.houston.filmographer.di

import com.houston.filmographer.presentation.search.SearchViewModel
import com.houston.filmographer.presentation.details.AboutViewModel
import com.houston.filmographer.presentation.details.PosterViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(interactor = get()) }
    viewModel { (movieId: String) -> AboutViewModel(movieId = movieId, interactor = get()) }
    viewModel { (posterUrl: String) -> PosterViewModel(posterUrl = posterUrl) }
}