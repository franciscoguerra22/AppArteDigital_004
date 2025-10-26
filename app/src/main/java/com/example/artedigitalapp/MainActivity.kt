package com.example.artedigitalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.artedigitalapp.ui.theme.ArteDigitalAppTheme
import com.example.artedigitalapp.screens.AppNavGraph

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Permite que la app use todo el espacio de la pantalla (Edge-to-Edge)
        enableEdgeToEdge()

        // Configura Compose como la UI principal
        setContent {
            ArteDigitalAppTheme {
                // Scaffold es la estructura base de la pantalla, podemos agregar topBar, bottomBar, etc.
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    // Llama a nuestra función de navegación que mostrará las pantallas
                    AppNavGraph()
                }
            }
        }
    }
}
