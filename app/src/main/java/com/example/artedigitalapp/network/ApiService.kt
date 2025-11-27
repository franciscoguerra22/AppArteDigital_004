package com.example.artedigitalapp.network

import com.example.artedigitalapp.models.AuthResponse
import com.example.artedigitalapp.models.LoginRequest
import com.example.artedigitalapp.models.RegistroRequest
import com.example.artedigitalapp.models.Servicio
import com.example.artedigitalapp.models.CompraResponse
import retrofit2.Response // Importar Response de Retrofit
import retrofit2.http.*

interface ApiService {

    // === AUTH ===
    @POST("/api/auth/login")
    suspend fun login(@Body loginRequest: LoginRequest): AuthResponse

    @POST("/api/auth/register")
    suspend fun register(@Body registroRequest: RegistroRequest): AuthResponse

    // === SERVICIOS ===
    @GET("/api/servicios/listar")
    suspend fun listarServicios(): List<Servicio>

    @GET("/api/servicios/{id}")
    suspend fun obtenerServicioPorId(@Path("id") id: Long): Servicio

    @POST("/api/servicios/crear")
    suspend fun crearServicio(@Body servicio: Servicio): Servicio

    @PUT("/api/servicios/{id}")
    suspend fun editarServicio(@Path("id") id: Long, @Body servicio: Servicio): Servicio

    // NUEVA FUNCIÓN PARA ELIMINAR SERVICIO (Requiere Token de Admin)
    @DELETE("/api/servicios/{id}")
    suspend fun eliminarServicio(@Path("id") id: Long)
    // Fin de la nueva función

    @PATCH("/api/servicios/{id}/desactivar")
    suspend fun desactivarServicio(@Path("id") id: Long): Servicio

    @PATCH("/api/servicios/{id}/activar")
    suspend fun activarServicio(@Path("id") id: Long): Servicio

    // === COMPRAS ===

    // Endpoint para obtener todas las compras del usuario autenticado (ID viene del JWT)
    @GET("api/compras/miscompras")
    suspend fun obtenerMisCompras(): List<CompraResponse>

    // CORRECCIÓN CLAVE: Acepta una lista de IDs en el cuerpo (@Body) y retorna Response<>
    @POST("api/compras/registrar")
    suspend fun registrarCompra(
        @Body servicioIds: List<Long> // AHORA espera una lista de Longs en el cuerpo
    ): Response<CompraResponse> // AHORA retorna Response<CompraResponse> para manejar códigos HTTP
}