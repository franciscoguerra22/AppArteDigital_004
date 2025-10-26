package com.example.artedigitalapp.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object Screens {
    const val Login = "login"
    const val Registro = "registro"
    const val Home = "home"
}

@Composable
fun AppNavGraph(navController: NavHostController = rememberNavController()) {
    NavHost(navController = navController, startDestination = Screens.Login) {
        composable(Screens.Login) { LoginScreen(navController) }
        composable(Screens.Registro) { RegistroScreen(navController) }
        composable(Screens.Home) { HomeScreen(navController) }
    }
}
