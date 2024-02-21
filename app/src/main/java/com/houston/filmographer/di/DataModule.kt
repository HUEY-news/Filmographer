package com.houston.filmographer.di

import android.content.Context
import com.houston.filmographer.data.impl.Storage
import com.houston.filmographer.data.network.NetworkClient
import com.houston.filmographer.data.network.RetrofitNetworkClient
import com.houston.filmographer.data.network.TvApiService
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val dataModule = module {

    single<NetworkClient> { RetrofitNetworkClient(context = androidContext(), service = get()) }
    single<TvApiService> {
        Retrofit.Builder()
            .baseUrl("https://tv-api.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(TvApiService::class.java)
    }

    single { Storage(prefs = get()) }
    single { androidContext().getSharedPreferences("local_storage", Context.MODE_PRIVATE) }

}