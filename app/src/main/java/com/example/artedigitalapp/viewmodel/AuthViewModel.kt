package com.example.artedigitalapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artedigitalapp.models.AuthResponse
import com.example.artedigitalapp.models.LoginRequest
import com.example.artedigitalapp.models.RegistroRequest
import com.example.artedigitalapp.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val repository = AuthRepository()

    // === Estado de la respuesta ===
    private val _authResponse = MutableStateFlow<AuthResponse?>(null)
    val authResponse: StateFlow<AuthResponse?> = _authResponse

    // === Estado de error ===
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // === Función de login ===
    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                val response = repository.login(LoginRequest(email, password))
                _authResponse.value = response
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }

    // === Función de registro ===
    fun register(
        nombreCompleto: String,
        email: String,
        password: String,
    ) {
        viewModelScope.launch {
            try {
                val response = repository.register(
                    RegistroRequest(nombreCompleto, email, password)
                )
                _authResponse.value = response
                _errorMessage.value = null
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}
