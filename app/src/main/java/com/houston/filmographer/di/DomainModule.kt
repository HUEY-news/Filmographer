package com.houston.filmographer.di

import com.houston.filmographer.data.converter.MovieCastConverter
import com.houston.filmographer.data.db.HistoryRepositoryImpl
import com.houston.filmographer.data.converter.MovieDbConvertor
import com.houston.filmographer.data.impl.MovieRepositoryImpl
import com.houston.filmographer.domain.search.MovieInteractor
import com.houston.filmographer.domain.search.MovieInteractorImpl
import com.houston.filmographer.domain.search.MovieRepository
import com.houston.filmographer.domain.db.HistoryInteractor
import com.houston.filmographer.domain.db.HistoryInteractorImpl
import com.houston.filmographer.domain.db.HistoryRepository
import org.koin.dsl.module

val interactorModule = module {
    single<MovieInteractor> { MovieInteractorImpl(repository = get()) }
    single<HistoryInteractor> { HistoryInteractorImpl(repository = get()) }
}

val repositoryModule = module {
    factory { MovieCastConverter() }
    factory { MovieDbConvertor(storage = get()) }

    single<MovieRepository> {
        MovieRepositoryImpl(
            client = get(),
            appDatabase = get(),
            movieCastConverter = get(),
            movieDbConvertor = get(),
            storage = get()
        )
    }

    single<HistoryRepository> {
        HistoryRepositoryImpl(
            appDatabase = get(),
            movieDbConvertor = get()
        )
    }
}