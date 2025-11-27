package com.example.artedigitalapp.repository

import com.example.artedigitalapp.models.CompraResponse
import com.example.artedigitalapp.network.ApiService
import retrofit2.Response

// Se asume que este es el repositorio que contiene la función registrarCompra

class CompraRepository(private val apiService: ApiService) {

    // Cambiamos el tipo de retorno para que coincida con el ApiService.
    // Devolvemos el objeto Response<CompraResponse> para que el ViewModel
    // pueda acceder al código de estado HTTP (201, 400, etc.) y al body/errorBody.
    suspend fun registrarCompra(servicioIds: List<Long>): Response<CompraResponse> {
        return apiService.registrarCompra(servicioIds)
    }

    // NUEVA FUNCIÓN: Para obtener las compras del usuario autenticado
    suspend fun obtenerMisCompras(): List<CompraResponse> {
        return apiService.obtenerMisCompras()
    }
}