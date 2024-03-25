package com.houston.filmographer.domain.db

import com.houston.filmographer.domain.search.model.Movie
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun historyMovies(): Flow<List<Movie>>
}