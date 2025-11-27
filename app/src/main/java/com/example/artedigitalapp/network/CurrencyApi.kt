package com.example.artedigitalapp.network

import com.example.artedigitalapp.model.CurrencyResponse
import retrofit2.http.GET

interface CurrencyApi {
    @GET("latest/CLP")
    suspend fun getRates(): CurrencyResponse
}
