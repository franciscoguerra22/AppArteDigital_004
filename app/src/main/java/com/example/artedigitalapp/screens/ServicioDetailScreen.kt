package com.example.artedigitalapp.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel // ⬅️ Importación clave
import coil.compose.rememberAsyncImagePainter
import com.example.artedigitalapp.models.Servicio
// ❌ ELIMINADA: Ya no se usa directamente
// import com.example.artedigitalapp.repository.ServicioRepository
import com.example.artedigitalapp.utils.vibrar
import com.example.artedigitalapp.viewmodel.ServiciosViewModel // ⬅️ Tu ViewModel

@Composable
fun ServicioDetailScreen(
    navController: NavController,
    servicioId: Long,
    onVolverClick: () -> Unit,
    onAgregarAlCarrito: (Servicio) -> Unit, // <-- parámetro agregado
    // 1. Inyectamos el ViewModel de Servicios
    viewModel: ServiciosViewModel = viewModel()
) {
    val context = LocalContext.current

    // 2. Observamos el estado del servicio a editar del ViewModel
    val servicio by viewModel.servicioAEditar.collectAsState()

    // 3. Obtenemos el estado de error (opcional)
    val error by viewModel.errorMessage.collectAsState()

    // Estado de carga local, se mantiene true hasta que el servicio no sea null
    var isLoading by remember { mutableStateOf(true) }


// 🔄 Cargar servicio desde el backend (CORRECCIÓN CLAVE)
    LaunchedEffect(servicioId) {
        // Marcamos isLoading para que el Box se muestre
        isLoading = true

        // Llamar al método del ViewModel que contiene la lógica del repositorio
        viewModel.cargarServicioParaEdicion(servicioId)

        // Esperamos brevemente o manejamos la carga a través del estado observado
        // Si el servicio se carga (o falla), el Composable se recompone
        isLoading = false
    }

    // 4. Determinar estados finales
    val finalServicio = servicio
    val finalError = error

    when {
        // Caso: Aún cargando (servicio es null)
        finalServicio == null && finalError == null && isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        // Caso: Error al cargar
        finalError != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(finalError!!)
            }
        }

        // Caso: Servicio cargado exitosamente
        finalServicio != null -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                val painter = rememberAsyncImagePainter(finalServicio.imagenUrl ?: "https://via.placeholder.com/300")

                Image(
                    painter = painter,
                    contentDescription = finalServicio.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp),
                    contentScale = ContentScale.Crop
                )

                Spacer(Modifier.height(16.dp))
                Text(finalServicio.nombre, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))
                Text(finalServicio.descripcion)
                Spacer(Modifier.height(8.dp))
                Text("Precio: \$${finalServicio.precio}")
                Spacer(Modifier.height(8.dp))

                // 🎯 INDICADOR DE ESTADO
                Text(
                    "Estado: ${if (finalServicio.activo) "Disponible" else "Desactivado por Admin"}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = if (finalServicio.activo) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
                )

                Spacer(Modifier.height(16.dp))

                // 🛒 Botón Agregar al carrito usando onAgregarAlCarrito
                Button(
                    onClick = {
                        onAgregarAlCarrito(finalServicio)
                        vibrar(context)
                        Toast.makeText(context, "Agregado al carrito", Toast.LENGTH_SHORT).show()
                    },
                    modifier = Modifier.fillMaxWidth(),
                    // 🎯 CLAVE DE RESTRICCIÓN: Solo habilitado si el servicio está activo
                    enabled = finalServicio.activo
                ) {
                    // 🎯 CLAVE DE MENSAJE: Texto descriptivo si está inactivo
                    Text(if (finalServicio.activo) "Agregar al Carrito" else "Servicio Desactivado")
                }

                Spacer(Modifier.height(8.dp))

                // 🔙 Botón Volver
                Button(
                    onClick = onVolverClick,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Volver")
                }
            }
        }
    }

}