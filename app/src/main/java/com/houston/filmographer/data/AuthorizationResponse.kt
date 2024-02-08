package com.houston.filmographer.data

import com.google.gson.annotations.SerializedName

class AuthorizationResponse(
    @SerializedName("access_token")
    val token: String
)