package com.example.artedigitalapp.repository

import com.example.artedigitalapp.models.AuthResponse
import com.example.artedigitalapp.models.LoginRequest
import com.example.artedigitalapp.models.RegistroRequest
import com.example.artedigitalapp.network.RetrofitClient

class AuthRepository {

    private val api = RetrofitClient.apiService

    // === Login ===
    suspend fun login(loginRequest: LoginRequest): AuthResponse {
        return api.login(loginRequest)
    }

    // === Registro ===
    suspend fun register(registroRequest: RegistroRequest): AuthResponse {
        return api.register(registroRequest)
    }
}
