@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.artedigitalapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext // Necesario para el Toast de éxito/error al eliminar
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.artedigitalapp.models.Servicio // Asumo que esta clase está definida
import com.example.artedigitalapp.viewmodel.ServiciosViewModel
import kotlinx.coroutines.launch
import android.widget.Toast // Necesario para mostrar mensajes

// Importaciones necesarias para el botón Eliminar
import androidx.compose.material3.ButtonDefaults // Para usar ButtonDefaults.buttonColors

@Composable
fun EditarServicioScreen(
    navController: NavController,
    servicioId: Long?,
    // 1. Inyectar el ViewModel
    viewModel: ServiciosViewModel = viewModel()
) {
    // 2. Observar el estado del servicio desde el ViewModel
    val servicioEnEdicion by viewModel.servicioAEditar.collectAsState()

    // Estados locales para la UI, inicializados con los datos del servicio observado
    var nombre by remember(servicioEnEdicion) { mutableStateOf(servicioEnEdicion?.nombre ?: "") }
    var descripcion by remember(servicioEnEdicion) { mutableStateOf(servicioEnEdicion?.descripcion ?: "") }
    var precio by remember(servicioEnEdicion) { mutableStateOf(servicioEnEdicion?.precio?.toString() ?: "") }
    var activo by remember(servicioEnEdicion) { mutableStateOf(servicioEnEdicion?.activo ?: true) }

    var isLoading by remember { mutableStateOf(true) } // Estado de carga inicial
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var mostrarDialogoEliminar by remember { mutableStateOf(false) } // <--- NUEVA variable para el diálogo de eliminación

    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current // Para el Toast de confirmación de eliminación

    // 3. Cargar servicio desde backend usando el ViewModel
    LaunchedEffect(servicioId) {
        if (servicioId != null) {
            isLoading = true
            errorMessage = null

            viewModel.cargarServicioParaEdicion(servicioId)

            isLoading = false
        } else {
            errorMessage = "ID inválido"
            isLoading = false
        }
    }

    // 4. Determinar si aún estamos cargando
    val isDataLoading = isLoading || (servicioId != null && servicioEnEdicion == null)

    if (isDataLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (errorMessage != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(errorMessage!!)
        }
        // No hay return aquí, lo que permite que el usuario intente corregir la entrada si el error es de validación
    }

    servicioEnEdicion?.let { s ->
        Scaffold(
            topBar = { TopAppBar(title = { Text("Editar Servicio") }) },
            content = { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding)
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        label = { Text("Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = descripcion,
                        onValueChange = { descripcion = it },
                        label = { Text("Descripción") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = precio,
                        onValueChange = { precio = it },
                        label = { Text("Precio") },
                        modifier = Modifier.fillMaxWidth()
                        // Aquí no añadimos KeyboardOptions si quieres mantener el formato original sin cambios
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Checkbox(
                            checked = activo,
                            onCheckedChange = { value -> activo = value }
                        )
                        Text("Disponible")
                    }

                    Spacer(modifier = Modifier.height(16.dp)) // Espaciador antes de botones

                    // Botón GUARDAR CAMBIOS
                    Button(
                        onClick = {
                            val precioNumerico = precio.toDoubleOrNull()
                            if (nombre.isBlank() || precioNumerico == null) {
                                errorMessage = "Completa los campos obligatorios."
                                return@Button
                            }

                            val actualizado = s.copy(
                                nombre = nombre,
                                descripcion = descripcion,
                                precio = precioNumerico,
                                activo = activo
                            )

                            isLoading = true
                            errorMessage = null

                            coroutineScope.launch {
                                try {
                                    // 5. Llamar al método de actualización del ViewModel
                                    viewModel.actualizarServicio(actualizado)
                                    navController.popBackStack()
                                } catch (e: Exception) {
                                    errorMessage = "Error al actualizar servicio: ${e.message}"
                                } finally {
                                    isLoading = false
                                }
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading
                    ) {
                        Text(if (isLoading) "Guardando..." else "Guardar cambios")
                    }

                    Spacer(modifier = Modifier.height(8.dp)) // Espaciador entre Guardar y Eliminar

                    // <--- NUEVO BOTÓN: Eliminar Servicio
                    Button(
                        onClick = {
                            mostrarDialogoEliminar = true // Abre el diálogo de confirmación
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                        modifier = Modifier.fillMaxWidth(),
                        enabled = !isLoading
                    ) {
                        Text("Eliminar Servicio", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        )
    }

    // <--- NUEVO DIÁLOGO DE CONFIRMACIÓN PARA ELIMINAR
    if (mostrarDialogoEliminar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoEliminar = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogoEliminar = false
                        coroutineScope.launch {
                            // Llamada al ViewModel para eliminar
                            viewModel.eliminarServicio(servicioId ?: -1) {
                                // Callback de éxito
                                navController.popBackStack()
                                Toast.makeText(context, "Servicio eliminado de la BD.", Toast.LENGTH_LONG).show()
                            }
                        }
                    }
                ) { Text("Confirmar Eliminación") }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoEliminar = false }) { Text("Cancelar") }
            },
            title = { Text("Confirmar Eliminación") },
            text = { Text("¿Está seguro de que desea eliminar permanentemente este servicio?") }
        )
    }
}