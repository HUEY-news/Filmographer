package com.houston.filmographer.di

import com.houston.filmographer.data.converter.MovieCastConverter
import com.houston.filmographer.data.impl.RepositoryImpl
import com.houston.filmographer.domain.Interactor
import com.houston.filmographer.domain.InteractorImpl
import com.houston.filmographer.domain.Repository
import org.koin.dsl.module

val interactorModule = module {
    factory<Interactor> { InteractorImpl(repository = get()) }
}

val repositoryModule = module {
    factory { MovieCastConverter() }

    single<Repository> {
        RepositoryImpl(
            client = get(),
            converter = get(),
            storage = get())
    }
}