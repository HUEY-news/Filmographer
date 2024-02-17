package com.houston.filmographer.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.houston.filmographer.data.dto.MovieRequest
import com.houston.filmographer.data.dto.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient(
    private val context: Context,
    private val service: TvApiService
): NetworkClient {

    private val baseUrl = "https://tv-api.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun doRequest(dto: Any): Response {
        if (isConnected() == false) return Response().apply { resultCode = -1 }
        if (dto !is MovieRequest) return Response().apply { resultCode = 400 }
        val response = service.searchMovie(dto.key, dto.expression).execute()
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