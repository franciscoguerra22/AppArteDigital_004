@file:OptIn(ExperimentalMaterial3Api::class)
package com.example.artedigitalapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import coil.compose.rememberAsyncImagePainter
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.artedigitalapp.navigation.Screens
import com.example.artedigitalapp.repository.UserSession
import com.example.artedigitalapp.repository.SessionManager
import com.example.artedigitalapp.screens.components.CarruselImagenes
import com.example.artedigitalapp.screens.components.CarruselItem
import com.example.artedigitalapp.viewmodel.CarritoViewModel
import com.example.artedigitalapp.viewmodel.CurrencyViewModel
import com.example.artedigitalapp.viewmodel.ServiciosViewModel
import kotlinx.coroutines.launch

@Composable
fun HomePrincipalScreen(
    navController: NavController,
    onNavigateToLogin: () -> Unit,
    onNavigateToRegistro: () -> Unit,
    // 1. Inyectar el ViewModel de Servicios
    serviciosViewModel: ServiciosViewModel = viewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    var nombreUsuario by remember { mutableStateOf("Invitado") }

    // 2. Observar el estado de los servicios desde el ViewModel
    val servicios by serviciosViewModel.servicios.collectAsState()

    // 3. El estado de carga y error debería venir del ViewModel, pero lo mantenemos simple por ahora
    var isLoading by remember { mutableStateOf(true) }
    var menuExpanded by remember { mutableStateOf(false) }

// ViewModel para la tasa de cambio
    val currencyViewModel: CurrencyViewModel = viewModel()

// ViewModel del carrito
    val carritoViewModel: CarritoViewModel = viewModel()
    val carrito by carritoViewModel.carrito.collectAsState()

// Cargar sesión y servicios
    LaunchedEffect(Unit) {
        SessionManager.loadSession(context)
        nombreUsuario = UserSession.nombre ?: "Invitado"

        // 4. Llamar al método del ViewModel en lugar del repositorio
        serviciosViewModel.listarServicios()

        // Asume que la lista se cargará en el ViewModel y luego se observará.
        // Simulamos la finalización de carga inicial.
        isLoading = false

        // Cargar carrito inicial
        carritoViewModel.cargarCarrito(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hola, $nombreUsuario") },
                actions = {
                    Box {
                        TextButton(onClick = { menuExpanded = true }) {
                            Text("Menú")
                        }

                        DropdownMenu(
                            expanded = menuExpanded,
                            onDismissRequest = { menuExpanded = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text("Carrito (${carrito.size})") },
                                onClick = {
                                    menuExpanded = false
                                    navController.navigate(Screens.Carrito.route)
                                }
                            )

                            if (UserSession.token == null) {
                                DropdownMenuItem(
                                    text = { Text("Iniciar sesión") },
                                    onClick = {
                                        menuExpanded = false
                                        onNavigateToLogin()
                                    }
                                )
                                DropdownMenuItem(
                                    text = { Text("Registrarse") },
                                    onClick = {
                                        menuExpanded = false
                                        onNavigateToRegistro()
                                    }
                                )
                            } else {

                                // 🎯 NUEVO: Opción Mis Compras para usuarios logeados
                                DropdownMenuItem(
                                    text = { Text("Mis Compras") },
                                    onClick = {
                                        menuExpanded = false
                                        // Navegaremos a esta ruta (necesita definirse en Screens.kt)
                                        navController.navigate(Screens.MisCompras.route)
                                    }
                                )
                                // FIN NUEVO

                                if (UserSession.role == "ROLE_ADMIN") {
                                    DropdownMenuItem(
                                        text = { Text("Servicios") },
                                        onClick = {
                                            menuExpanded = false
                                            navController.navigate(Screens.ServiciosAdmin.route)
                                        }
                                    )
                                }
                                if (UserSession.role == "ROLE_ADMIN") {
                                    DropdownMenuItem(
                                        text = { Text("Usuarios") },
                                        onClick = {
                                            menuExpanded = false
                                            navController.navigate(Screens.UsuariosAdmin.route)
                                        }
                                    )
                                }

                                DropdownMenuItem(
                                    text = { Text("Cerrar sesión") },
                                    onClick = {
                                        menuExpanded = false
                                        UserSession.token = null
                                        UserSession.nombre = null
                                        UserSession.role = null
                                        nombreUsuario = "Invitado"
                                        SessionManager.clearSession(context)
                                    }
                                )
                            }
                        }
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
                // Usamos la observación de servicios para determinar si hay datos
                isLoading && servicios.isEmpty() -> CircularProgressIndicator(
                    modifier = Modifier.align(
                        Alignment.Center
                    )
                )

                servicios.isEmpty() -> Text(
                    "No hay servicios disponibles",
                    modifier = Modifier.align(Alignment.Center)
                )

                else -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {

                    // TASA USD → Mostrar CurrencyScreen
                    item {
                        // Asumo que CurrencyScreen ya está definida
                        CurrencyScreen(viewModel = currencyViewModel)
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // Carrusel de servicios baratos
                    val serviciosBaratos = servicios.filter { it.precio < 10000.0 }

                    if (serviciosBaratos.isNotEmpty()) {
                        item {
                            CarruselImagenes(
                                items = serviciosBaratos.map {
                                    CarruselItem(
                                        titulo = it.nombre,
                                        imagenUrl = it.imagenUrl
                                            ?: "https://via.placeholder.com/400",
                                        precio = it.precio,
                                        idServicio = it.id
                                    )
                                },
                                onClick = { item ->
                                    item.idServicio?.let {
                                        navController.navigate("${Screens.ServicioDetail.route}/$it")
                                    }
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                    }

                    // Lista principal de servicios
                    items(servicios) { servicio ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate("${Screens.ServicioDetail.route}/${servicio.id}")
                                },
                            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                val imagenPainter = rememberAsyncImagePainter(
                                    servicio.imagenUrl ?: "https://via.placeholder.com/300"
                                )
                                Image(
                                    painter = imagenPainter,
                                    contentDescription = servicio.nombre,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(10.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(Modifier.height(8.dp))
                                Text(servicio.nombre, style = MaterialTheme.typography.titleLarge)
                                Text(servicio.descripcion)
                                Spacer(Modifier.height(4.dp))
                                Text(
                                    "Precio: \$${servicio.precio}",
                                    style = MaterialTheme.typography.bodyLarge
                                )
                                Text(
                                    "Disponible: ${if (servicio.activo) "Sí" else "No"}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}