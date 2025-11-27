package com.example.artedigitalapp.model

data class CurrencyResponse(
    val result: String,
    val base_code: String,
    val conversion_rates: Map<String, Double>
)
