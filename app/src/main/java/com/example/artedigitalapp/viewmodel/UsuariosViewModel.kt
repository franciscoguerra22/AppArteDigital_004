package com.example.artedigitalapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artedigitalapp.models.UsuarioResponse
import com.example.artedigitalapp.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuariosViewModel : ViewModel() {

    private val _usuarios = MutableStateFlow<List<UsuarioResponse>>(emptyList())
    val usuarios: StateFlow<List<UsuarioResponse>> = _usuarios

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    // 🔥 ESTA ES LA FUNCIÓN QUE FALTABA
    fun listarUsuarios() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val api = RetrofitClient.apiService
                val response = api.listarUsuarios()   // <<--- AHORA SÍ EXISTE

                _usuarios.value = response

            } catch (e: Exception) {
                _error.value = e.message
            }

            _isLoading.value = false
        }
    }
}
