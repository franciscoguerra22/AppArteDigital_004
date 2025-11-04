@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.artedigitalapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.artedigitalapp.repository.ServicioRepository
import com.example.artedigitalapp.repository.CarritoRepository

@Composable
fun HomePrincipalScreen(
    navController: NavController,
    onNavigateToLogin: () -> Unit,
    onNavigateToRegistro: () -> Unit
) {
    val servicios = ServicioRepository.obtenerServicios()
    var carritoCount by remember { mutableStateOf(CarritoRepository.obtenerCarrito().size) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Servicios") },
                actions = {
                    // Botón para ir al carrito
                    TextButton(onClick = { navController.navigate("carrito") }) {
                        Text("Carrito ($carritoCount)")
                    }
                    TextButton(onClick = onNavigateToLogin) { Text("Iniciar sesión") }
                    TextButton(onClick = onNavigateToRegistro) { Text("Registrarse") }
                }
            )
        }
    ) { padding ->
        if (servicios.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                Text("No hay servicios disponibles")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(servicios) { servicio ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                // Navegar al detalle del servicio (sin guardar lambdas)
                                navController.navigate("detalle/${servicio.id}")
                            },
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Image(
                                painter = painterResource(id = servicio.imagenRes),
                                contentDescription = servicio.titulo,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(180.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(Modifier.height(8.dp))
                            Text(servicio.titulo, style = MaterialTheme.typography.titleLarge)
                            Text(servicio.descripcion)
                            Spacer(Modifier.height(4.dp))
                            Text("Precio: \$${servicio.precio}", style = MaterialTheme.typography.bodyLarge)
                            Text(
                                "Disponible: ${if (servicio.disponible) "Sí" else "No"}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }
            }
        }
    }
}
