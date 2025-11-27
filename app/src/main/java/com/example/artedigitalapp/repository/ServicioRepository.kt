package com.example.artedigitalapp.repository

import com.example.artedigitalapp.models.Servicio
import com.example.artedigitalapp.network.ApiService
import retrofit2.HttpException
import java.lang.IllegalArgumentException

// Ahora la dependencia 'api' se inyecta por el constructor.
class ServicioRepository(private val api: ApiService) {

    // Obtener lista de servicios desde backend
    suspend fun obtenerServiciosBackend(): List<Servicio> {
        return try {
            api.listarServicios()
        } catch (e: HttpException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    // Obtener un servicio por ID desde backend
    suspend fun obtenerServicioPorIdBackend(id: Long): Servicio {
        return try {
            api.obtenerServicioPorId(id)
        } catch (e: Exception) {
            throw e
        }
    }

    // **Editar servicio en backend**
    suspend fun actualizarServicioBackend(servicio: Servicio): Servicio {
        val servicioId = servicio.id
            ?: throw IllegalArgumentException("El servicio no tiene ID asignado")
        return try {
            api.editarServicio(servicioId, servicio)
        } catch (e: Exception) {
            throw e
        }
    }

    // Crear un nuevo servicio
    suspend fun crearServicioBackend(servicio: Servicio): Servicio {
        return try {
            api.crearServicio(servicio)
        } catch (e: Exception) {
            throw e
        }
    }

    // Activar/desactivar servicios
    suspend fun activarServicioBackend(id: Long): Servicio {
        return try {
            api.activarServicio(id)
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun desactivarServicioBackend(id: Long): Servicio {
        return try {
            api.desactivarServicio(id)
        } catch (e: Exception) {
            throw e
        }
    }

    // NUEVA FUNCIÓN: Eliminar un servicio por ID REAL
    suspend fun eliminarServicioBackend(id: Long) {
        return try {
            api.eliminarServicio(id)
        } catch (e: Exception) {
            throw e
        }
    }
    // Fin de la nueva función
}