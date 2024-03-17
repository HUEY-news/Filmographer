package com.houston.filmographer.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.houston.filmographer.data.dto.details.MovieDetailsRequest
import com.houston.filmographer.data.dto.movie.MovieSearchRequest
import com.houston.filmographer.data.dto.Response
import com.houston.filmographer.data.dto.cast.MovieCastRequest
import com.houston.filmographer.data.dto.name.NameSearchRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RetrofitNetworkClient(
    private val context: Context,
    private val service: TvApiService
): NetworkClient {

    override suspend fun doRequest(dto: Any): Response {

        if (!isConnected()) return Response().apply { resultCode = -1 }

        if ((dto !is MovieSearchRequest)
            && (dto !is MovieDetailsRequest)
            && (dto !is MovieCastRequest)
            && (dto !is NameSearchRequest))
            return Response().apply { resultCode = 400 }


        return withContext(Dispatchers.IO) {
            try {
                val response = when (dto) {
                    is NameSearchRequest -> service.searchName(dto.key, dto.expression)
                    is MovieSearchRequest -> service.searchMovie(dto.key, dto.expression)
                    is MovieDetailsRequest -> service.getMovieDetails(dto.key, dto.movieId)
                    else -> service.getMovieCast((dto as MovieCastRequest).key, dto.movieId)
                }
                response.apply { resultCode = 200 }
            } catch (exception: Throwable) {
               Response().apply { resultCode = 500 }
            }
        }
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