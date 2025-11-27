package com.example.artedigitalapp.models

import com.google.gson.annotations.SerializedName

data class CompraResponse(
    val id: Long,
    val usuarioId: Long,
    val servicioId: Long,
    val valor: Double,
    val fechaCompra: String,  // O LocalDateTime si usas un converter
    val estado: String
)
