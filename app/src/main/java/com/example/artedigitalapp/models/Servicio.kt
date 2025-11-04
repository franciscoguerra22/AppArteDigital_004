package com.example.artedigitalapp.models
import androidx.annotation.DrawableRes

data class Servicio(
    val id: Int,
    val titulo: String,
    val descripcion: String,
    val precio: Int,
    @DrawableRes val imagenRes: Int, // ID del drawable
    val disponible: Boolean = true
)
