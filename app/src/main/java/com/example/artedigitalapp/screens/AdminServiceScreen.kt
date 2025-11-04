package com.example.artedigitalapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdminServiceScreen(
    onLogoutClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Panel de Administrador", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = onLogoutClick, modifier = Modifier.fillMaxWidth()) {
            Text("Cerrar sesión")
        }

        // Aquí más adelante agregarás CRUD de servicios
    }
}
