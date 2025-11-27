package com.example.artedigitalapp.models

data class Servicio(
    val id: Long? = null,          // Coincide con el backend
    val nombre: String,            // Nombre del servicio
    val descripcion: String,
    val precio: Double,
    val activo: Boolean = true,    // Disponible o no
    val imagenUrl: String? = null  // URL de imagen desde backend
)


