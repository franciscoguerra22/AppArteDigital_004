package com.example.artedigitalapp.models

data class LoginResponse(
    val nombre: String,
    val token: String,
    val role: String
)
