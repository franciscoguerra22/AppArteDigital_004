package com.example.artedigitalapp.repository

import com.example.artedigitalapp.models.Servicio

object CarritoRepository {

    // Lista interna que almacena los servicios agregados al carrito
    private val carrito: MutableList<Servicio> = mutableListOf()

    // Función para obtener todos los servicios del carrito
    fun obtenerCarrito(): List<Servicio> = carrito.toList()

    // Función para agregar un servicio al carrito
    fun agregarAlCarrito(servicio: Servicio) {
        carrito.add(servicio)
    }

    // Función para eliminar un servicio del carrito (opcional)
    fun eliminarDelCarrito(servicio: Servicio) {
        carrito.remove(servicio)
    }

    // Función para vaciar el carrito (opcional)
    fun vaciarCarrito() {
        carrito.clear()
    }
}
