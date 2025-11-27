@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.artedigitalapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel // ⬅️ Importación necesaria
import coil.compose.rememberAsyncImagePainter
// ❌ ELIMINADA: Ya no se usa directamente
// import com.example.artedigitalapp.repository.ServicioRepository
import com.example.artedigitalapp.viewmodel.ServiciosViewModel // ⬅️ Tu ViewModel

@Composable
fun ServiciosScreen(
    navController: NavController,
    onVolverClick: () -> Unit,
    // 1. Inyectar el ViewModel de Servicios
    viewModel: ServiciosViewModel = viewModel()
) {
    // 2. Observar el estado de los servicios desde el ViewModel
    val servicios by viewModel.servicios.collectAsState()

    // 3. Observar el estado de error
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Estado de carga local, se mantiene true hasta que se ejecute la primera carga
    var isLoading by remember { mutableStateOf(true) }

    // Cargar servicios desde backend usando el ViewModel (CORRECCIÓN CLAVE)
    LaunchedEffect(Unit) {
        // 4. Llamar al método del ViewModel para obtener la lista
        viewModel.listarServicios()
        // Después de iniciar la corrutina en el VM, la carga inicial se considera terminada.
        isLoading = false
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Servicios de Ilustración") },
                navigationIcon = {
                    TextButton(onClick = onVolverClick) {
                        Text("← Volver")
                    }
                }
            )
        }
    ) { padding ->
        // 5. Modificar la lógica para usar los estados observados
        when {
            // Caso de carga inicial: si isLoading es true Y la lista aún está vacía
            isLoading && servicios.isEmpty() && errorMessage == null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Error al cargar servicios: $errorMessage", color = MaterialTheme.colorScheme.error)
                }
            }

            servicios.isEmpty() -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No hay servicios disponibles")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(servicios) { servicio ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    // Navegación a la pantalla de detalle, asumiendo la ruta correcta
                                    navController.navigate("detalle/${servicio.id}")
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                // Usar Coil para imagen desde URL
                                Image(
                                    painter = rememberAsyncImagePainter(servicio.imagenUrl),
                                    contentDescription = servicio.nombre,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(180.dp),
                                    contentScale = ContentScale.Crop
                                )

                                Spacer(Modifier.height(8.dp))
                                Text(servicio.nombre, style = MaterialTheme.typography.titleLarge)
                                Text(servicio.descripcion)
                                Spacer(Modifier.height(4.dp))
                                Text("Precio: \$${servicio.precio}", style = MaterialTheme.typography.bodyLarge)
                                Text(
                                    "Activo: ${if (servicio.activo) "Sí" else "No"}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}