package com.example.artedigitalapp.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.artedigitalapp.models.LoginRequest
import com.example.artedigitalapp.repository.AuthRepository
import com.example.artedigitalapp.repository.CarritoRepository
import com.example.artedigitalapp.repository.UserSession
import kotlinx.coroutines.launch

// --- Rutas centralizadas ---
object Screens {
    const val HomePrincipal = "home_principal"
    const val Login = "login"
    const val Registro = "registro"
    const val Home = "home"
    const val Servicios = "servicios"
    const val AdminService = "admin_service"
    const val ServicioDetail = "detalle"
    const val Carrito = "carrito"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    NavHost(navController = navController, startDestination = Screens.HomePrincipal) {

        // 🏠 Pantalla principal
        composable(Screens.HomePrincipal) {
            HomePrincipalScreen(
                navController = navController,
                onNavigateToLogin = { navController.navigate(Screens.Login) },
                onNavigateToRegistro = { navController.navigate(Screens.Registro) }
            )
        }

        // 🔑 Login
        composable(Screens.Login) {
            LoginScreen(
                onLoginClick = { email, password ->
                    scope.launch {
                        try {
                            val response = AuthRepository().login(LoginRequest(email, password))
                            UserSession.token = response.token
                            UserSession.role = response.role

                            // Redirección según el rol
                            if (response.role == "ADMIN") {
                                navController.navigate(Screens.AdminService) {
                                    popUpTo(Screens.Login) { inclusive = true }
                                }
                            } else {
                                navController.navigate(Screens.Home) {
                                    popUpTo(Screens.Login) { inclusive = true }
                                }
                            }
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error de login: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                onNavigateToRegistro = { navController.navigate(Screens.Registro) }
            )
        }

        // 📝 Registro
        composable(Screens.Registro) {
            RegistroScreen(
                onRegisterClick = { nombre, email, password ->
                    Toast.makeText(context, "Registro exitoso: $nombre", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.Login) {
                        popUpTo(Screens.Registro) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(Screens.Login) {
                        popUpTo(Screens.Registro) { inclusive = true }
                    }
                }
            )
        }

        // 🏡 Home (post login)
        composable(Screens.Home) {
            HomeScreen(
                onLogoutClick = {
                    UserSession.clear()
                    navController.navigate(Screens.HomePrincipal) {
                        popUpTo(Screens.Home) { inclusive = true }
                    }
                },
                onVerServiciosClick = { navController.navigate(Screens.Servicios) }
            )
        }

        // 🎨 Servicios disponibles
        composable(Screens.Servicios) {
            ServiciosScreen(
                navController = navController,
                onVolverClick = { navController.popBackStack() }
            )
        }

        // 🖼 Detalle del servicio
        composable(
            route = "${Screens.ServicioDetail}/{servicioId}",
            arguments = listOf(navArgument("servicioId") { type = NavType.IntType })
        ) { backStackEntry ->
            val servicioId = backStackEntry.arguments?.getInt("servicioId") ?: 0

            ServicioDetailScreen(
                servicioId = servicioId,
                onVolverClick = { navController.popBackStack() },
                onAgregarAlCarrito = { servicio ->
                    CarritoRepository.agregarAlCarrito(servicio)
                    Toast.makeText(context, "${servicio.titulo} agregado al carrito", Toast.LENGTH_SHORT).show()
                }
            )
        }

        // 🛒 Carrito
        composable(Screens.Carrito) {
            CarritoScreen(
                onVolverClick = { navController.popBackStack() }
            )
        }

        // ⚙️ Panel de administración
        composable(Screens.AdminService) {
            AdminServiceScreen(
                onLogoutClick = {
                    UserSession.clear()
                    navController.navigate(Screens.HomePrincipal) {
                        popUpTo(Screens.AdminService) { inclusive = true }
                    }
                }
            )
        }
    }
}
