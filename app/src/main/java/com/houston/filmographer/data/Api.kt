package com.houston.filmographer.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface Api {

    @POST("/translate/yoda.json")
    fun translateToYoda(
        @Body request: TranslationRequest
    ): Call<TranslationResponse>

    @POST("/translate/morse.json")
    fun translateToMorse(
        @Body request: TranslationRequest
    ): Call<TranslationResponse>
}