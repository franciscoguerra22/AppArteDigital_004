package com.example.artedigitalapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.artedigitalapp.repository.CarritoRepository
import com.example.artedigitalapp.models.Servicio

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    onVolverClick: () -> Unit
) {
    var carrito by remember { mutableStateOf(CarritoRepository.obtenerCarrito()) }
    var mostrarDialogo by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito de Compras") },
                navigationIcon = {
                    TextButton(onClick = onVolverClick) {
                        Text("← Volver")
                    }
                },
                actions = {
                    if (carrito.isNotEmpty()) {
                        TextButton(onClick = { mostrarDialogo = true }) {
                            Text("Vaciar carrito")
                        }
                    }
                }
            )
        }
    ) { padding ->
        if (carrito.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No hay servicios en el carrito")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(carrito) { servicio ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
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

                            Spacer(Modifier.height(8.dp))
                            Button(
                                onClick = {
                                    CarritoRepository.eliminarDelCarrito(servicio)
                                    carrito = CarritoRepository.obtenerCarrito()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(45.dp)
                            ) {
                                Text("Eliminar del carrito")
                            }
                        }
                    }
                }
            }
        }
    }

    // Diálogo de confirmación para vaciar el carrito
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        CarritoRepository.vaciarCarrito()
                        carrito = CarritoRepository.obtenerCarrito()
                        mostrarDialogo = false
                    }
                ) {
                    Text("Vaciar todo")
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) {
                    Text("Cancelar")
                }
            },
            title = { Text("Vaciar carrito") },
            text = { Text("¿Seguro que deseas eliminar todos los servicios del carrito?") }
        )
    }
}
