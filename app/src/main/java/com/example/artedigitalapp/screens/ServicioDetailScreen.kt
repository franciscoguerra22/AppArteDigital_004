@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
package com.example.artedigitalapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.artedigitalapp.models.Servicio
import com.example.artedigitalapp.repository.ServicioRepository

// Esta lambda se ejecutará al agregar un servicio al carrito
typealias OnAgregarAlCarrito = (Servicio) -> Unit

@Composable
fun ServicioDetailScreen(
    servicioId: Int,
    onVolverClick: () -> Unit,
    onAgregarAlCarrito: OnAgregarAlCarrito // <-- agregamos este parámetro
) {
    val servicio: Servicio? = ServicioRepository.obtenerServicios().find { it.id == servicioId }

    if (servicio == null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Servicio no encontrado")
            Spacer(Modifier.height(20.dp))
            Button(onClick = onVolverClick) { Text("Volver") }
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TopAppBar(
            title = { Text(servicio.titulo) },
            navigationIcon = {
                TextButton(onClick = onVolverClick) {
                    Text("← Volver")
                }
            }
        )

        Spacer(Modifier.height(16.dp))

        // Imagen usando drawable local
        Image(
            painter = painterResource(id = servicio.imagenRes),
            contentDescription = servicio.titulo,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop
        )

        Spacer(Modifier.height(16.dp))

        Text("Título: ${servicio.titulo}", style = MaterialTheme.typography.titleLarge)
        Text("Descripción: ${servicio.descripcion}", style = MaterialTheme.typography.bodyMedium)
        Text("Precio: \$${servicio.precio}", style = MaterialTheme.typography.bodyLarge)
        Text("Disponible: ${if (servicio.disponible) "Sí" else "No"}", style = MaterialTheme.typography.bodyMedium)

        Spacer(Modifier.height(24.dp))

        // Botón Agregar al Carrito
        Button(
            onClick = { onAgregarAlCarrito(servicio) },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Agregar al carrito")
        }
    }
}
