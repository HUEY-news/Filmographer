package com.houston.filmographer.data.network

import com.houston.filmographer.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}