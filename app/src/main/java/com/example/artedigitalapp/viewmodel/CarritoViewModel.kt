package com.example.artedigitalapp.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artedigitalapp.models.Servicio
import com.example.artedigitalapp.network.ApiService
import com.example.artedigitalapp.network.RetrofitClient
import com.example.artedigitalapp.utils.CartManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Response

class CarritoViewModel : ViewModel() {

    // 1. Inicialización del servicio de API (se mantiene, pero no se usará en 'realizarCompra')
    private val apiService: ApiService = RetrofitClient.apiService

    private val _carrito = MutableStateFlow<List<Servicio>>(emptyList())
    val carrito: StateFlow<List<Servicio>> = _carrito

    fun cargarCarrito(context: Context) {
        _carrito.value = CartManager.loadCart(context)
    }

    fun agregar(context: Context, servicio: Servicio) {
        val actual = _carrito.value.toMutableList()
        actual.add(servicio)
        _carrito.value = actual

        CartManager.saveCart(context, actual)
    }

    fun borrarItem(context: Context, servicio: Servicio) {
        val actual = _carrito.value.toMutableList()
        actual.remove(servicio)
        _carrito.value = actual

        CartManager.saveCart(context, actual)
    }

    fun borrarCarrito(context: Context) {
        _carrito.value = emptyList()
        CartManager.clearCart(context)
    }

    /**
     * 🚨 MODIFICADO: FUNCIÓN DE SIMULACIÓN LOCAL
     * Al apretar comprar, NO se comunica con el backend.
     * Simplemente vacía el carrito y muestra éxito.
     */
    fun realizarCompra(context: Context, onCompraExitosa: () -> Unit) {
        if (_carrito.value.isEmpty()) {
            Toast.makeText(context, "El carrito está vacío.", Toast.LENGTH_SHORT).show()
            return
        }

        // SIMULACIÓN: Ejecutar la lógica de éxito inmediatamente

        // 1. Vaciar el carrito (éxito simulado)
        borrarCarrito(context)

        // 2. Mostrar mensaje de éxito
        Toast.makeText(context, "Compra completada exitosamente.", Toast.LENGTH_LONG).show()

        // 3. Ejecutar el callback de la UI (si existe)
        onCompraExitosa()

    }
}