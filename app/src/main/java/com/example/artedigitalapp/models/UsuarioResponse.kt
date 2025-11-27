package com.example.artedigitalapp.models

data class UsuarioResponse(
    val id: Long,
    val nombreCompleto: String,
    val email: String,
    val role: String
)
