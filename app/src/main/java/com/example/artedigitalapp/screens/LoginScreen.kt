package com.example.artedigitalapp.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun LoginScreen(navController: NavController) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Pantalla de Login")

        Button(onClick = {
            navController.navigate("registro")
        }) {
            Text(text = "Ir a Registro")
        }

        Button(onClick = {
            navController.navigate("home")
        }) {
            Text(text = "Ir a Home")
        }
    }
}
