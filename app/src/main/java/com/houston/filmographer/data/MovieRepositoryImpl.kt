package com.houston.filmographer.data

import com.houston.filmographer.data.dto.MovieRequest
import com.houston.filmographer.data.dto.MovieResponse
import com.houston.filmographer.data.network.NetworkClient
import com.houston.filmographer.domain.Movie
import com.houston.filmographer.domain.MovieRepository
import com.houston.filmographer.util.Resource

class MovieRepositoryImpl(private val client: NetworkClient): MovieRepository {

    override fun searchMovie(key: String, expression: String): Resource<List<Movie>> {
        val response = client.doRequest(MovieRequest(key, expression))
        when (response.resultCode) {

            -1 -> return Resource.Error("Проверьте подключение к интернету")

            200 -> {
                val movies = (response as MovieResponse).results.map {
                Movie(it.id, it.resultType, it.image, it.title, it.description)}
                return Resource.Success(movies)
            }

            else -> return Resource.Error("Сервер не отвечает")
        }
    }
}