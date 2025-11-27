package com.example.artedigitalapp.network

import com.example.artedigitalapp.models.UsuarioResponse
import com.example.artedigitalapp.models.AuthResponse
import com.example.artedigitalapp.models.LoginRequest
import com.example.artedigitalapp.models.RegistroRequest
import com.example.artedigitalapp.models.Servicio
import com.example.artedigitalapp.models.CompraResponse
import retrofit2.Response
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

    @DELETE("/api/servicios/{id}")
    suspend fun eliminarServicio(@Path("id") id: Long)

    @PATCH("/api/servicios/{id}/desactivar")
    suspend fun desactivarServicio(@Path("id") id: Long): Servicio

    @PATCH("/api/servicios/{id}/activar")
    suspend fun activarServicio(@Path("id") id: Long): Servicio

    // === COMPRAS ===
    @GET("/api/compras/miscompras")
    suspend fun obtenerMisCompras(): List<CompraResponse>

    @POST("/api/compras/registrar")
    suspend fun registrarCompra(
        @Body servicioIds: List<Long>
    ): Response<CompraResponse>

    // === USUARIOS (Solo Admin) ===
    @GET("/api/usuarios/listar")
    suspend fun listarUsuarios(): List<UsuarioResponse>
}
