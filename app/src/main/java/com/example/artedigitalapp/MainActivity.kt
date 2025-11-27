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
import com.example.artedigitalapp.repository.SessionManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // ✅ Cargar sesión guardada ANTES de mostrar pantallas
        SessionManager.loadSession(this)

        enableEdgeToEdge()

        setContent {
            ArteDigitalAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold { innerPadding ->
                        AppNavGraph()
                    }
                }
            }
        }
    }
}
