package com.example.artedigitalapp.models

data class AuthResponse(
    val id: Long?,
    val nombre: String,
    val token: String,
    val role: String
)
