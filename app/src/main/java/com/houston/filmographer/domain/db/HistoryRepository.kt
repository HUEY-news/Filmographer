package com.houston.filmographer.domain.db

import com.houston.filmographer.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface HistoryRepository {
    fun historyMovies(): Flow<List<Movie>>
}