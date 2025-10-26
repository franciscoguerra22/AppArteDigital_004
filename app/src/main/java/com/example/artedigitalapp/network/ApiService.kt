package com.example.artedigitalapp.network

import com.example.artedigitalapp.models.AuthResponse
import com.example.artedigitalapp.models.LoginRequest
import com.example.artedigitalapp.models.RegistroRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    // === LOGIN ===
    @POST("/api/auth/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): AuthResponse

    // === REGISTRO ===
    @POST("/api/auth/register")
    suspend fun register(
        @Body registroRequest: RegistroRequest
    ): AuthResponse
}
