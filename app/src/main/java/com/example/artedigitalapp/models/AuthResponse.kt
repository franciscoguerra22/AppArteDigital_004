package com.example.artedigitalapp.models

data class AuthResponse(
    val token: String,
    val role: String // "CLIENTE" o "ADMIN"
)
