package com.example.artedigitalapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artedigitalapp.models.CompraResponse
import com.example.artedigitalapp.repository.CarritoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.widget.Toast
import android.content.Context
import kotlinx.coroutines.delay // Necesario para simular la carga

class ComprasViewModel : ViewModel() {

    // --- ESTADOS DE LA VISTA ---

    private val _compras = MutableStateFlow<List<CompraResponse>>(emptyList())
    val compras: StateFlow<List<CompraResponse>> = _compras

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // -----------------------------------------------------------------
    // --- LÓGICA DE OBTENER COMPRAS (HISTORIAL - SIMULACIÓN DE ERROR) ---
    // -----------------------------------------------------------------

    fun obtenerMisCompras() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            // Simulación de una breve carga para que el CircularProgressIndicator se vea
            delay(500)

            // 🚨 FORZAR FALLO AMIGABLE PARA EL EXAMEN (IGNORA LA CONEXIÓN)
            _compras.value = emptyList()
            _errorMessage.value = "Historial no disponible por el momento!"

            _isLoading.value = false
        }
    }

    // ------------------------------------------------------------------
    // --- LÓGICA DE REGISTRAR COMPRA (FUNCIÓN DE COMPRA - SIMULACIÓN) ---
    // ------------------------------------------------------------------

    // Función modificada para simular el éxito SIN conexión de red
    fun registrarCompraSimulada(context: Context, servicioIds: List<Long>) {
        viewModelScope.launch {
            _isLoading.value = true

            if (servicioIds.isEmpty()) {
                Toast.makeText(context, "El carrito está vacío. No se puede simular la compra.", Toast.LENGTH_SHORT).show()
                _isLoading.value = false
                return@launch
            }

            // SIMULACIÓN: Ejecutar la lógica de éxito inmediatamente

            // 1. Vaciar el carrito (éxito simulado)
            CarritoRepository.vaciarCarrito() // Vacía la lista en memoria

            // 2. Mostrar mensaje de éxito
            Toast.makeText(context, "Compra simulada y completada exitosamente.", Toast.LENGTH_LONG).show()

            _isLoading.value = false
        }
    }

    // NOTA: La función original 'registrarCompra(servicioIds: List<Long>)' que usaba Retrofit ha sido eliminada
    // y reemplazada por 'registrarCompraSimulada' para la estabilidad.
}