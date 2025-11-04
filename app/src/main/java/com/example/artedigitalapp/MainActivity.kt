package com.example.artedigitalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.artedigitalapp.screens.AppNavGraph
import com.example.artedigitalapp.ui.theme.ArteDigitalAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            // 🌙 Tema global estilo Bootstrap Dark
            ArteDigitalAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold { innerPadding ->
                        // 🚀 Aquí se carga toda la navegación de pantallas
                        AppNavGraph()
                    }
                }
            }
        }
    }
}
