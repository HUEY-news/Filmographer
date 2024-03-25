package com.houston.filmographer.data.impl

import com.houston.filmographer.data.converter.MovieCastConverter
import com.houston.filmographer.data.converter.MovieDbConvertor
import com.houston.filmographer.data.db.AppDatabase
import com.houston.filmographer.data.dto.cast.MovieCastRequest
import com.houston.filmographer.data.dto.cast.MovieCastResponse
import com.houston.filmographer.data.dto.details.MovieDetailsRequest
import com.houston.filmographer.data.dto.details.MovieDetailsResponse
import com.houston.filmographer.data.dto.movie.MovieDto
import com.houston.filmographer.data.dto.movie.MovieSearchRequest
import com.houston.filmographer.data.dto.movie.MovieSearchResponse
import com.houston.filmographer.data.dto.name.NameSearchRequest
import com.houston.filmographer.data.dto.name.NameSearchResponse
import com.houston.filmographer.data.network.NetworkClient
import com.houston.filmographer.domain.search.MovieRepository
import com.houston.filmographer.domain.search.model.Movie
import com.houston.filmographer.domain.search.model.MovieCast
import com.houston.filmographer.domain.search.model.MovieDetails
import com.houston.filmographer.domain.search.model.Person
import com.houston.filmographer.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl(
    private val client: NetworkClient,
    private val appDatabase: AppDatabase,
    private val movieCastConverter: MovieCastConverter,
    private val movieDbConvertor: MovieDbConvertor,
    private val storage: MovieStorage
): MovieRepository {

    override fun searchMovie(key: String, expression: String): Flow<Resource<List<Movie>>> = flow {
        val response = client.doRequest(MovieSearchRequest(key, expression))
        when (response.resultCode) {

            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))

            200 -> {
                val stored = storage.getSavedFavorites()
                val results = (response as MovieSearchResponse).results
                val data = results.map {
                Movie(
                    id = it.id,
                    resultType = it.resultType,
                    image = it.image,
                    title = it.title,
                    description = it.description,
                    inFavorite = stored.contains(it.id))
                }
                saveMovie(results)
                emit(Resource.Success(data))
            }

            else -> emit(Resource.Error("Сервер не отвечает"))
        }
    }

    override fun searchName(key: String, expression: String): Flow<Resource<List<Person>>> = flow {
        val response = client.doRequest(NameSearchRequest(key, expression))
        when (response.resultCode) {
            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))

            200 -> {
                val data = (response as NameSearchResponse).results.map {
                    Person(
                        id = it.id,
                        name = it.title,
                        description = it.description,
                        photoUrl = it.image
                    )
                }
                emit(Resource.Success(data))
            }

            else -> emit(Resource.Error("Сервер не отвечает"))
        }
    }

    override fun getMovieDetails(key: String, movieId: String): Flow<Resource<MovieDetails>> = flow {
        val response = client.doRequest(MovieDetailsRequest(key, movieId))
        when (response.resultCode) {

            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))

            200 -> {
                val data = with (response as MovieDetailsResponse) {
                    MovieDetails(
                        id = id,
                        title = title,
                        imDbRating = imDbRating,
                        year = year,
                        countries = countries,
                        genres = genres,
                        directors = directors,
                        writers = writers,
                        stars = stars,
                        plot = plot
                    )
                }
                emit(Resource.Success(data))
            }

            else -> emit(Resource.Error("Сервер не отвечает"))
        }
    }

    override fun getMovieCast(key: String, movieId: String): Flow<Resource<MovieCast>> = flow {
        val response = client.doRequest(MovieCastRequest(key, movieId))
        when (response.resultCode) {

            -1 -> emit(Resource.Error("Проверьте подключение к интернету"))

            200 -> {
                val data = movieCastConverter.convert(response as MovieCastResponse)
                emit(Resource.Success(data))
            }

            else -> emit(Resource.Error("Сервер не отвечает"))
        }
    }

    private suspend fun saveMovie(movies: List<MovieDto>) {
        val movieEntities = movies.map { movie -> movieDbConvertor.map(movie) }
        appDatabase.movieDao().insertMovies(movieEntities)
    }

    override fun addMovieToFavorites(movie: Movie) { storage.addToFavorites(movie.id) }
    override fun removeMovieFromFavorites(movie: Movie) { storage.removeFromFavorites(movie.id) }
}