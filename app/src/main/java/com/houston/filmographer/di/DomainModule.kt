package com.houston.filmographer.di

import com.houston.filmographer.data.impl.MovieRepositoryImpl
import com.houston.filmographer.domain.api.MovieInteractor
import com.houston.filmographer.domain.impl.MovieInteractorImpl
import com.houston.filmographer.domain.repository.MovieRepository
import org.koin.dsl.module

val interactorModule = module {
    factory<MovieInteractor> { MovieInteractorImpl(repository = get()) }
}

val repositoryModule = module {
    single<MovieRepository> { MovieRepositoryImpl(client = get(), localStorage = get()) }
}