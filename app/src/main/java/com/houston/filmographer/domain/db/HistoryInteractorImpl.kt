package com.houston.filmographer.domain.db

import com.houston.filmographer.domain.model.Movie
import kotlinx.coroutines.flow.Flow

class HistoryInteractorImpl(
    private val repository: HistoryRepository
): HistoryInteractor {
    override fun historyMovies(): Flow<List<Movie>> {
        return repository.historyMovies()
    }
}