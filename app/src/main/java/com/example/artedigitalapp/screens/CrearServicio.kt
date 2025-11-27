@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.artedigitalapp.screens

import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.* import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.artedigitalapp.models.Servicio
import com.example.artedigitalapp.viewmodel.ServiciosViewModel
import kotlinx.coroutines.launch

// ❌ Quitamos las importaciones problemáticas

@Composable
fun CrearServicioScreen(
    navController: NavController,
    viewModel: ServiciosViewModel = viewModel()
) {
    var nombre by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }
    var activo by remember { mutableStateOf(true) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Crear Servicio") }) },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {

                // 1. Campo Nombre
                OutlinedTextField(
                    value = nombre,
                    onValueChange = { nombre = it },
                    label = { Text("Nombre del Servicio") },
                    modifier = Modifier.fillMaxWidth()
                )

                // 2. Campo Descripción
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text("Descripción") },
                    modifier = Modifier.fillMaxWidth()
                )

                // 3. Campo Precio
                OutlinedTextField(
                    value = precio,
                    onValueChange = { precio = it },
                    label = { Text("Precio") },
                    // 🎯 SOLUCIÓN: Quitamos la propiedad keyboardOptions
                    modifier = Modifier.fillMaxWidth()
                )

                // 4. Checkbox Activo
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = activo,
                        onCheckedChange = { activo = it }
                    )
                    Spacer(Modifier.width(8.dp))
                    Text("Activo")
                }

                if (errorMessage != null) {
                    Text(errorMessage!!, color = MaterialTheme.colorScheme.error)
                }

                Button(
                    onClick = {
                        val precioNumerico = precio.toDoubleOrNull()
                        if (nombre.isBlank() || descripcion.isBlank() || precioNumerico == null || precioNumerico < 0) {
                            errorMessage = "Por favor, completa todos los campos correctamente."
                            return@Button
                        }

                        val nuevoServicio = Servicio(
                            id = null,
                            nombre = nombre,
                            descripcion = descripcion,
                            precio = precioNumerico,
                            activo = activo
                        )

                        isLoading = true
                        errorMessage = null

                        coroutineScope.launch {
                            try {
                                viewModel.crearServicio(nuevoServicio)
                                navController.popBackStack()
                            } catch (e: Exception) {
                                errorMessage = "Error al crear servicio: ${e.message}"
                            } finally {
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = !isLoading
                ) {
                    Text(if (isLoading) "Creando..." else "Guardar")
                }
            }
        }
    )
}