package com.example.artedigitalapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artedigitalapp.models.Servicio
import com.example.artedigitalapp.network.RetrofitClient // Asegúrate de tener esta clase e importación
import com.example.artedigitalapp.repository.ServicioRepository // Asegúrate de tener esta clase e importación
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// 🚨 CORRECCIÓN CLAVE: El constructor debe aceptar el repositorio.
class ServiciosViewModel(
    private val repository: ServicioRepository = ServicioRepository(RetrofitClient.apiService)
) : ViewModel() {

    // --- ESTADOS ---

    // Estado para la lista de servicios a mostrar.
    private val _servicios = MutableStateFlow<List<Servicio>>(emptyList())
    val servicios: StateFlow<List<Servicio>> = _servicios

    // Estado para el servicio que se está editando o cargando por ID.
    private val _servicioAEditar = MutableStateFlow<Servicio?>(null)
    val servicioAEditar: StateFlow<Servicio?> = _servicioAEditar

    // Estado para manejar errores en cualquier operación.
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    // Estado de carga global (AÑADIDO para gestionar estados de UI)
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // --- MÉTODOS DE LÓGICA DE NEGOCIO (CRUD) ---

    /**
     * Carga todos los servicios del backend.
     */
    fun listarServicios() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _errorMessage.value = null
                val result = repository.obtenerServiciosBackend()
                _servicios.value = result
            } catch (e: Exception) {
                _errorMessage.value = "Error al listar servicios: ${e.message}"
                _servicios.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Carga un servicio específico por ID para la pantalla de edición.
     */
    fun cargarServicioParaEdicion(servicioId: Long) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _errorMessage.value = null
                _servicioAEditar.value = repository.obtenerServicioPorIdBackend(servicioId)
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar servicio: ${e.message}"
                _servicioAEditar.value = null
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Registra un nuevo servicio.
     */
    fun crearServicio(servicio: Servicio) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _errorMessage.value = null
                val nuevoServicio = repository.crearServicioBackend(servicio)

                // Opcional: Si quieres que la lista se actualice inmediatamente:
                _servicios.value = _servicios.value + nuevoServicio

            } catch (e: Exception) {
                _errorMessage.value = "Error al crear servicio: ${e.message}"
                // Relanzar la excepción para que la UI (Composable) pueda manejar la navegación
                throw e
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Actualiza un servicio existente.
     */
    fun actualizarServicio(servicio: Servicio) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _errorMessage.value = null
                val servicioActualizado = repository.actualizarServicioBackend(servicio)

                // Opcional: Actualizar el servicio en la lista principal
                _servicios.value = _servicios.value.map {
                    if (it.id == servicioActualizado.id) servicioActualizado else it
                }

            } catch (e: Exception) {
                _errorMessage.value = "Error al actualizar servicio: ${e.message}"
                // Relanzar la excepción para que la UI pueda manejar el error
                throw e
            } finally {
                _isLoading.value = false
            }
        }
    }

    /**
     * Elimina un servicio existente y ejecuta un callback de éxito.
     */
    fun eliminarServicio(id: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                _errorMessage.value = null
                repository.eliminarServicioBackend(id)

                // Eliminar el servicio de la lista local
                _servicios.value = _servicios.value.filter { it.id != id }

                // Limpiar el servicio en edición
                if (_servicioAEditar.value?.id == id) {
                    _servicioAEditar.value = null
                }

                onSuccess() // Notifica a la pantalla que la eliminación fue exitosa

            } catch (e: Exception) {
                _errorMessage.value = "Error al eliminar servicio: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}