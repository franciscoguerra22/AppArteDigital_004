package com.example.artedigitalapp.repository

object UserSession {
    var token: String? = null
    var role: String? = null
    var nombre: String? = null
    // --- AÑADE ESTA PROPIEDAD ---
    var id: Long? = null
    // ----------------------------

    fun clear() {
        token = null
        role = null
        nombre = null
        id = null // Limpiar el ID al cerrar sesión
    }
}