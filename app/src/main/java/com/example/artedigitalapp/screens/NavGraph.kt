package com.example.artedigitalapp.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.artedigitalapp.navigation.Screens
import com.example.artedigitalapp.viewmodel.CarritoViewModel

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()
    val carritoViewModel: CarritoViewModel = viewModel() // <- ViewModel sin Hilt

    NavHost(
        navController = navController,
        startDestination = Screens.HomePrincipal.route
    ) {

        // Home Principal
        composable(Screens.HomePrincipal.route) {
            HomePrincipalScreen(
                navController = navController,
                onNavigateToLogin = { navController.navigate(Screens.Login.route) },
                onNavigateToRegistro = { navController.navigate(Screens.Registro.route) }
            )
        }

        // Login
        composable(Screens.Login.route) {
            LoginScreen(
                onNavigateToRegistro = { navController.navigate(Screens.Registro.route) },
                onLoginSuccess = {
                    navController.navigate(Screens.HomePrincipal.route) {
                        popUpTo(Screens.Login.route) { inclusive = true }
                    }
                }
            )
        }

        // Registro
        composable(Screens.Registro.route) {
            RegistroScreen(
                onNavigateToLogin = {
                    navController.navigate(Screens.Login.route) {
                        popUpTo(Screens.Registro.route) { inclusive = true }
                    }
                }
            )
        }

        // Servicios Admin
        composable(Screens.ServiciosAdmin.route) {
            ServiciosAdminScreen(navController = navController)
        }

        // Crear Servicio
        composable(Screens.CrearServicio.route) {
            CrearServicioScreen(navController = navController)
        }

        // 🎯 NUEVO: Mis Compras
        composable(Screens.MisCompras.route) {
            MisComprasScreen(navController = navController) // <--- DESTINO AGREGADO
        }

        // Editar Servicio
        composable(
            route = "${Screens.EditarServicio.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L
            EditarServicioScreen(
                navController = navController,
                servicioId = id
            )
        }

        // Servicio Detail (con CarritoViewModel)
        composable(
            route = "${Screens.ServicioDetail.route}/{id}",
            arguments = listOf(navArgument("id") { type = NavType.LongType })
        ) { entry ->
            val id = entry.arguments?.getLong("id") ?: 0L

            ServicioDetailScreen(
                navController = navController,
                servicioId = id,
                onVolverClick = { navController.popBackStack() },
                onAgregarAlCarrito = { servicio ->
                    carritoViewModel.agregar(navController.context, servicio)
                }
            )
        }

        // Carrito
        composable(Screens.Carrito.route) {
            CarritoScreen(
                carritoViewModel = carritoViewModel,
                onVolverClick = { navController.popBackStack() }
            )
        }
    }
}