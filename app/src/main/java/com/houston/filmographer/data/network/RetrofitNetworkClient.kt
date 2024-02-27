package com.houston.filmographer.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.houston.filmographer.data.dto.details.MovieDetailsRequest
import com.houston.filmographer.data.dto.movie.MovieSearchRequest
import com.houston.filmographer.data.dto.Response
import com.houston.filmographer.data.dto.cast.MovieCastRequest
import com.houston.filmographer.data.dto.name.NameSearchRequest

class RetrofitNetworkClient(
    private val context: Context,
    private val service: TvApiService
): NetworkClient {

    override fun doRequest(dto: Any): Response {

        if (isConnected() == false) {
            val response = Response().apply { resultCode = -1 }
            return response
        }

        if ((dto !is MovieSearchRequest)
            && (dto !is MovieDetailsRequest)
            && (dto !is MovieCastRequest)
            && (dto !is NameSearchRequest)) {
            val response = Response().apply { resultCode = 400 }
            return response
        }

        val response = when (dto) {
                is NameSearchRequest -> service.searchName(dto.key, dto.expression).execute()
                is MovieSearchRequest -> service.searchMovie(dto.key, dto.expression).execute()
                is MovieDetailsRequest -> service.getMovieDetails(dto.key, dto.movieId).execute()
                else -> service.getMovieCast((dto as MovieCastRequest).key, dto.movieId).execute()
            }

        val body = response.body()
        if (body != null) return body.apply { resultCode = response.code() }
        else return Response().apply { resultCode = response.code() }
    }

    private fun isConnected(): Boolean {
        val connectManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities = connectManager.getNetworkCapabilities(connectManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }
}