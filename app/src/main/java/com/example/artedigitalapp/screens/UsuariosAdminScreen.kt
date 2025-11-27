@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.example.artedigitalapp.screens.admin

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.artedigitalapp.viewmodel.UsuariosViewModel

@Composable
fun UsuariosAdminScreen(
    navController: NavController,
    viewModel: UsuariosViewModel = viewModel()
) {
    val usuarios by viewModel.usuarios.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.listarUsuarios()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Usuarios Registrados") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            when {
                isLoading -> CircularProgressIndicator()

                error != null -> Text("Error: $error")

                usuarios.isEmpty() -> Text("No hay usuarios registrados")

                else -> LazyColumn {
                    items(usuarios) { u ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text("ID: ${u.id}")
                                Text("Nombre: ${u.nombreCompleto}")
                                Text("Email: ${u.email}")
                                Text("Rol: ${u.role}")
                            }
                        }
                    }
                }
            }
        }
    }
}
