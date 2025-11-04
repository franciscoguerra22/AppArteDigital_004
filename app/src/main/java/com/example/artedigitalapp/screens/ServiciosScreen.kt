@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.artedigitalapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.artedigitalapp.repository.ServicioRepository
import androidx.navigation.NavController

@Composable
fun ServiciosScreen(
    navController: NavController,
    onVolverClick: () -> Unit
) {
    val servicios = ServicioRepository.obtenerServicios()

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
                            navController.navigate("detalle/${servicio.id}")
                        },
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // <-- Aquí cambiamos a drawable
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
