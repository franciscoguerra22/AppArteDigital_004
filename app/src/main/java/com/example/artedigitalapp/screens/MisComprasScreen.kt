@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.artedigitalapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.artedigitalapp.viewmodel.ComprasViewModel // Importar el ViewModel
import java.text.NumberFormat
import java.util.*

@Composable
fun MisComprasScreen(
    navController: NavController,
    viewModel: ComprasViewModel = viewModel()
) {
    // 1. Observar el estado del ViewModel
    val compras by viewModel.compras.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // 2. Cargar las compras al entrar a la pantalla
    LaunchedEffect(Unit) {
        viewModel.obtenerMisCompras()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mis Compras") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                // Estado de carga
                isLoading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

                // Estado de error
                errorMessage != null -> Text(
                    "Error: $errorMessage",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.align(Alignment.Center).padding(16.dp)
                )

                // Lista vacía
                compras.isEmpty() -> Text(
                    "Aún no has realizado ninguna compra.",
                    modifier = Modifier.align(Alignment.Center)
                )

                // Lista de compras
                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(vertical = 10.dp)
                ) {
                    items(compras) { compra ->
                        CompraCard(compra = compra)
                    }
                }
            }
        }
    }
}

@Composable
fun CompraCard(compra: com.example.artedigitalapp.models.CompraResponse) {
    // Formato de moneda para el valor
    val format = NumberFormat.getCurrencyInstance(Locale("es", "CL")) // Ajusta la localización si es necesario

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // ID de la compra (Opcional)
            Text("Compra #${compra.id}", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))

            // Valor y Estado
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = format.format(compra.valor),
                    style = MaterialTheme.typography.headlineSmall
                )

                AssistChip(
                    onClick = { /* No interactivo */ },
                    label = { Text(compra.estado) },
                    colors = AssistChipDefaults.assistChipColors(
                        containerColor = if (compra.estado == "COMPLETADA") MaterialTheme.colorScheme.tertiaryContainer else MaterialTheme.colorScheme.errorContainer
                    )
                )
            }
            Spacer(Modifier.height(8.dp))

            // IDs de Servicio y Usuario (Estos IDs son internos, pero pueden ser útiles)
            Text(
                text = "ID Servicio: ${compra.servicioId} | Fecha: ${compra.fechaCompra}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}