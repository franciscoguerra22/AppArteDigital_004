package com.example.artedigitalapp.repository

import com.example.artedigitalapp.models.Servicio
import com.example.artedigitalapp.R

object ServicioRepository {

    // Simula datos locales (más adelante se conectará al backend)
    private val servicios = listOf(
        Servicio(
            id = 1,
            titulo = "Retrato Digital",
            descripcion = "Ilustración estilo retrato en alta resolución.",
            precio = 25000,
            imagenRes = R.drawable.servicio1
        ),
        Servicio(
            id = 2,
            titulo = "Ilustración Personalizada",
            descripcion = "Diseño único según tu idea o referencia.",
            precio = 35000,
            imagenRes = R.drawable.servicio2
        ),
        Servicio(
            id = 3,
            titulo = "Banner para Redes Sociales",
            descripcion = "Banner o portada lista para Instagram, Twitch, etc.",
            precio = 20000,
            imagenRes = R.drawable.servicio3
        ),
        Servicio(
            id = 4,
            titulo = "Arte Conceptual",
            descripcion = "Ilustración conceptual para proyectos o videojuegos.",
            precio = 40000,
            imagenRes = R.drawable.servicio4
        ),
        Servicio(
            id = 5,
            titulo = "Caricatura Personalizada",
            descripcion = "Dibujo divertido con estilo caricaturesco.",
            precio = 22000,
            imagenRes = R.drawable.servicio5
        )
    )

    fun obtenerServicios(): List<Servicio> = servicios
}
