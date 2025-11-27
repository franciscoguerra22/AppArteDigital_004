package com.example.artedigitalapp.repository

import com.example.artedigitalapp.network.RetrofitInstance

class CurrencyRepository {
    suspend fun getRates() = RetrofitInstance.api.getRates()
}

