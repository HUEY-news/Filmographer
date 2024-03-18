package com.houston.filmographer.domain

import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.domain.model.MovieCast
import com.houston.filmographer.domain.model.MovieDetails
import com.houston.filmographer.domain.model.Person
import com.houston.filmographer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class InteractorImpl(
    private val repository: Repository
) : Interactor {

    override fun searchMovie(key: String, expression: String): Flow<Pair<List<Movie>?, String?>> {
            return repository.searchMovie(key, expression).map { result ->
                when (result) {
                    is Resource.Success -> Pair(result.data, null)
                    is Resource.Error -> Pair(null, result.message)
                }
            }
    }

    override fun searchName(key: String, expression: String): Flow<Pair<List<Person>?, String?>> {
        return repository.searchName(key, expression).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

    override fun getMovieDetails(key: String, movieId: String): Flow<Pair<MovieDetails?, String?>> {
        return repository.getMovieDetails(key, movieId).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

    override fun getMovieCast(key: String, movieId: String): Flow<Pair<MovieCast?, String?>> {
        return repository.getMovieCast(key, movieId).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

    override fun addMovieToFavorites(movie: Movie) { repository.addMovieToFavorites(movie) }
    override fun removeMovieFromFavorites(movie: Movie) { repository.removeMovieFromFavorites(movie) }
}