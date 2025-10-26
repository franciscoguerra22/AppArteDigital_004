package com.example.artedigitalapp.screens

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Screens {
    const val Login = "login"
    const val Registro = "registro"
    const val Home = "home"
}

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = Screens.Login) {

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

        composable(Screens.Home) {
            HomeScreen(
                onLogoutClick = {
                    navController.navigate(Screens.Login) {
                        popUpTo(Screens.Home) { inclusive = true }
                    }
                }
            )
        }
    }
}
