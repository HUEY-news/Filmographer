package com.houston.filmographer.data

import com.houston.filmographer.data.dto.MovieRequest
import com.houston.filmographer.data.dto.MovieResponse
import com.houston.filmographer.data.network.NetworkClient
import com.houston.filmographer.domain.Movie
import com.houston.filmographer.domain.MovieRepository

class MovieRepositoryImpl(
    private val client: NetworkClient
): MovieRepository {

    override fun searchMovie(
        key: String,
        expression: String
    ): List<Movie> {
        val response = client.doRequest(MovieRequest(key, expression))
        if (response.resultCode == 200) {
            return (response as MovieResponse).results.map {
                Movie(it.id, it.resultType, it.image, it.title, it.description)
            }
        } else return emptyList()
    }
}