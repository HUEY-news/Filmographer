package com.houston.filmographer.di

import com.houston.filmographer.presentation.movie.view_model.MovieViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MovieViewModel(interactor = get()) }
}