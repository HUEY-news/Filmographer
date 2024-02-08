package com.houston.filmographer.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface ApplicationProgrammingInterface {

    @POST("/authorize/token?expire_hours=-1")
    fun authorize(
        @Body request: AuthorizationRequest
    ): Call<AuthorizationResponse>

    @GET("/api/v1/location/search/{query}")
    fun getLocations(
        @Header("Authorization") token: String,
        @Path("query") query: String
    ): Call<LocationResponse>

    @GET("/api/v1/current/{location}")
    fun getWeather(
        @Header("Authorization") token: String,
        @Path("location") locationId: Int
    ): Call<WeatherResponse>
}
