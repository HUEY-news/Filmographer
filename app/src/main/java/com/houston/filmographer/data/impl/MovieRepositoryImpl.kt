package com.houston.filmographer.data.impl

import com.houston.filmographer.data.converter.MovieCastConverter
import com.houston.filmographer.data.dto.cast.MovieCastRequest
import com.houston.filmographer.data.dto.cast.MovieCastResponse
import com.houston.filmographer.data.dto.details.MovieDetailsRequest
import com.houston.filmographer.data.dto.details.MovieDetailsResponse
import com.houston.filmographer.data.dto.movie.MovieRequest
import com.houston.filmographer.data.dto.movie.MovieResponse
import com.houston.filmographer.data.network.NetworkClient
import com.houston.filmographer.domain.model.Movie
import com.houston.filmographer.domain.model.MovieCast
import com.houston.filmographer.domain.model.MovieDetails
import com.houston.filmographer.domain.repository.MovieRepository
import com.houston.filmographer.util.Resource

class MovieRepositoryImpl(
    private val client: NetworkClient,
    private val converter: MovieCastConverter,
    private val storage: LocalStorage
): MovieRepository {

    override fun searchMovie(key: String, expression: String): Resource<List<Movie>> {
        val response = client.doRequest(MovieRequest(key, expression))
        when (response.resultCode) {

            -1 -> return Resource.Error("Проверьте подключение к интернету")

            200 -> {
                val stored = storage.getSavedFavorites()
                val movies = (response as MovieResponse).results.map {
                Movie(
                    id = it.id,
                    resultType = it.resultType,
                    image = it.image,
                    title = it.title,
                    description = it.description,
                    inFavorite = stored.contains(it.id))
                }
                return Resource.Success(movies)
            }

            else -> return Resource.Error("Сервер не отвечает")
        }
    }

    override fun getMovieDetails(key: String, movieId: String): Resource<MovieDetails> {
        val response = client.doRequest(MovieDetailsRequest(key, movieId))
        when (response.resultCode) {

            -1 -> return Resource.Error("Проверьте подключение к интернету")

            200 -> {
                val movieDetails = with (response as MovieDetailsResponse) {
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
                return Resource.Success(movieDetails)
            }

            else -> return Resource.Error("Сервер не отвечает")
        }
    }

    override fun getMovieCast(key: String, movieId: String): Resource<MovieCast> {
        val response = client.doRequest(MovieCastRequest(key, movieId))
        when (response.resultCode) {

            -1 -> return Resource.Error("Проверьте подключение к интернету")

            200 -> {
                val data = converter.convert(response as MovieCastResponse)
                return Resource.Success(data)
            }

            else -> return Resource.Error("Сервер не отвечает")
        }
    }

    override fun addMovieToFavorites(movie: Movie) { storage.addToFavorites(movie.id) }
    override fun removeMovieFromFavorites(movie: Movie) { storage.removeFromFavorites(movie.id) }
}