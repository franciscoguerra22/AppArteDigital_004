@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.artedigitalapp.screens

import android.widget.Toast
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.artedigitalapp.models.Servicio
import com.example.artedigitalapp.repository.UserSession // Mantener UserSession para verificar autenticación
import com.example.artedigitalapp.viewmodel.CarritoViewModel
// Importaciones de red y repositorio innecesarias aquí porque usamos el ViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CarritoScreen(
    carritoViewModel: CarritoViewModel,
    onVolverClick: () -> Unit
) {
    val context = LocalContext.current
    // Eliminamos 'scope' y 'compraRepository' porque la lógica de red está en el ViewModel

    LaunchedEffect(true) {
        carritoViewModel.cargarCarrito(context)
    }

    val carrito by carritoViewModel.carrito.collectAsState()
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
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(carrito) { servicio ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {

                            Image(
                                painter = rememberAsyncImagePainter(servicio.imagenUrl),
                                contentDescription = servicio.nombre,
                                modifier = Modifier
                                    // CORRECCIÓN: Aumentar la altura de la imagen para que se vea
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentScale = ContentScale.Crop
                            )

                            Spacer(Modifier.height(8.dp))
                            Text(servicio.nombre, style = MaterialTheme.typography.titleLarge)
                            Text(servicio.descripcion)
                            Spacer(Modifier.height(4.dp))
                            Text("Precio: \$${servicio.precio}", style = MaterialTheme.typography.bodyLarge)

                            Spacer(Modifier.height(8.dp))

                            Button(
                                onClick = { carritoViewModel.borrarItem(context, servicio) },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(35.dp)
                            ) {
                                Text("Eliminar del carrito")
                            }
                        }
                    }
                }

                item {
                    Spacer(Modifier.height(16.dp))
                    Button(
                        onClick = {
                            val usuarioAutenticado = UserSession.token != null
                            if (!usuarioAutenticado) {
                                Toast.makeText(context, "Error: Debes iniciar sesión para comprar.", Toast.LENGTH_LONG).show()
                                return@Button
                            }
                            carritoViewModel.realizarCompra(context) {

                            }

                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp), //
                        enabled = carrito.isNotEmpty()
                    ) {
                        Text("Comprar Todo (${carrito.size} items)")
                    }
                    Spacer(Modifier.height(32.dp))
                }
            }
        }
    }

    // AlertDialog para vaciar el carrito
    if (mostrarDialogo) {
        AlertDialog(
            onDismissRequest = { mostrarDialogo = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        carritoViewModel.borrarCarrito(context)
                        mostrarDialogo = false
                    }
                ) { Text("Vaciar todo") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogo = false }) { Text("Cancelar") }
            },
            title = { Text("Vaciar carrito") },
            text = { Text("¿Seguro que deseas eliminar todos los servicios del carrito?") }
        )
    }
}