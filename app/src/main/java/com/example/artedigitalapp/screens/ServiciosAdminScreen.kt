@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.artedigitalapp.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel // ⬅️ Importación necesaria
import com.example.artedigitalapp.models.Servicio
import com.example.artedigitalapp.navigation.Screens
// ❌ ELIMINADA: Ya no se usa directamente
// import com.example.artedigitalapp.repository.ServicioRepository
import com.example.artedigitalapp.viewmodel.ServiciosViewModel // ⬅️ Importar el ViewModel

@Composable
fun ServiciosAdminScreen(
    navController: NavController,
    // 1. Inyectar el ViewModel
    viewModel: ServiciosViewModel = viewModel()
) {
    // 2. Observar el estado de los servicios desde el ViewModel
    val servicios by viewModel.servicios.collectAsState()

    // 3. El estado de carga y error ahora puede basarse en la observación del ViewModel
    var isLoading by remember { mutableStateOf(true) } // Estado de carga inicial
    val errorMessage by viewModel.errorMessage.collectAsState() // Observar errores del VM

    // Cargar servicios desde backend usando el ViewModel
    LaunchedEffect(Unit) {
        // 4. Llamar al método del ViewModel en lugar del repositorio
        viewModel.listarServicios()
        isLoading = false // La carga inicial termina, ahora dependemos del estado del VM
    }

    // Nota: Es importante que la lista se recargue cuando se vuelve de crear/editar un servicio.
    // Esto lo puedes gestionar llamando a viewModel.listarServicios() en el LaunchedEffect
    // si usas un parámetro de navegación para forzar la recarga, o si gestionas el ciclo de vida del VM.

    Scaffold(
        topBar = { TopAppBar(title = { Text("Administrar Servicios") }) },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                navController.navigate(Screens.CrearServicio.route)
            }) {
                Text("+")
            }
        }
    ) { padding ->
        // 5. Modificar la lógica para usar los estados observados
        when {
            // Mantenemos isLoading local para la primera carga, pero la condición de Empty/Error
            // se basa en la lista observada y el error observado.
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
                    Text("Error al cargar servicios: $errorMessage")
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
                                    // Se asume que el ID es Long, por eso se pasa directamente:
                                    navController.navigate("${Screens.EditarServicio.route}/${servicio.id}")
                                }
                            ,
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(servicio.nombre, style = MaterialTheme.typography.titleLarge)
                                Spacer(Modifier.height(4.dp))
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