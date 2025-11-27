package com.example.artedigitalapp.repository

import com.example.artedigitalapp.models.AuthResponse
import com.example.artedigitalapp.models.LoginRequest
import com.example.artedigitalapp.models.RegistroRequest
import com.example.artedigitalapp.network.RetrofitClient
import org.json.JSONObject
import retrofit2.HttpException

class AuthRepository {

    private val api = RetrofitClient.apiService

    // === LOGIN ===
    suspend fun login(loginRequest: LoginRequest): AuthResponse {
        return try {
            api.login(loginRequest)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = parseBackendError(errorBody)
            throw Exception(message)
        } catch (e: Exception) {
            throw Exception("Error desconocido")
        }
    }

    // === REGISTRO ===
    suspend fun register(registroRequest: RegistroRequest): AuthResponse {
        return try {
            api.register(registroRequest)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val message = parseBackendError(errorBody)
            throw Exception(message)
        } catch (e: Exception) {
            throw Exception("Error desconocido")
        }
    }

    // === LEE EL MENSAJE DEL BACKEND CORRECTAMENTE ===
    private fun parseBackendError(errorBody: String?): String {
        return try {
            if (errorBody.isNullOrEmpty()) {
                return "Error inesperado"
            }

            val json = JSONObject(errorBody)

            // El backend siempre devuelve: { "error": "mensaje" }
            json.optString("error", "Error inesperado")

        } catch (e: Exception) {
            "Error en el servidor"
        }
    }
}
