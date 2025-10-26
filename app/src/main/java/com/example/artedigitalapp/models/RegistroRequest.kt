package com.example.artedigitalapp.models

data class RegistroRequest(
    val nombreCompleto: String,
    val email: String,
    val password: String,
    val telefono: String,
    val region: String,
    val comuna: String
)
