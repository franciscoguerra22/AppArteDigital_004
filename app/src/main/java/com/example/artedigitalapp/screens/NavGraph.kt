package com.example.artedigitalapp.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

// --- Todas las pantallas importadas desde el mismo package ---
import com.example.artedigitalapp.screens.HomePrincipalScreen
import com.example.artedigitalapp.screens.LoginScreen
import com.example.artedigitalapp.screens.RegistroScreen
import com.example.artedigitalapp.screens.HomeScreen

object Screens {
    const val HomePrincipal = "home_principal"
    const val Login = "login"
    const val Registro = "registro"
    const val Home = "home"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screens.HomePrincipal) {

        // 🏠 Pantalla de bienvenida / portada
        composable(Screens.HomePrincipal) {
            HomePrincipalScreen(
                onNavigateToLogin = { navController.navigate(Screens.Login) },
                onNavigateToRegistro = { navController.navigate(Screens.Registro) }
            )
        }

        // 🔑 Pantalla de inicio de sesión
        composable(Screens.Login) {
            LoginScreen(
                onLoginClick = { email, password ->
                    Toast.makeText(context, "Login: $email / $password", Toast.LENGTH_SHORT).show()
                    navController.navigate(Screens.Home) {
                        popUpTo(Screens.Login) { inclusive = true }
                    }
                },
                onNavigateToRegistro = {
                    navController.navigate(Screens.Registro)
                }
            )
        }

        // 📝 Pantalla de registro
        composable(Screens.Registro) {
            RegistroScreen(
                onRegisterClick = { nombre, email, password ->
                    Toast.makeText(context, "Registro: $nombre / $email / $password", Toast.LENGTH_SHORT).show()
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

        // 🖼 Pantalla Home (después de iniciar sesión)
        composable(Screens.Home) {
            HomeScreen(
                onLogoutClick = {
                    navController.navigate(Screens.HomePrincipal) {
                        popUpTo(Screens.Home) { inclusive = true }
                    }
                }
            )
        }
    }
}
