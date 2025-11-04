package com.example.artedigitalapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.example.artedigitalapp.R

// Colores inspirados en Bootstrap dark
private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF0D6EFD),      // Azul Bootstrap
    secondary = Color(0xFF6C757D),    // Gris secundario
    background = Color(0xFF121212),   // Fondo oscuro
    surface = Color(0xFF1E1E1E),      // Superficie tarjetas
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.White,
    onSurface = Color.White
)

// Fuente parecida a Bootstrap (usa Roboto)
val BootstrapFont = FontFamily.Default // Puedes usar una personalizada si quieres

@Composable
fun ArteDigitalAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = DarkColorScheme
    val typography = Typography(
        bodyLarge = Typography().bodyLarge.copy(fontFamily = BootstrapFont),
        titleLarge = Typography().titleLarge.copy(fontFamily = BootstrapFont),
        labelLarge = Typography().labelLarge.copy(fontFamily = BootstrapFont)
    )

    MaterialTheme(
        colorScheme = colorScheme,
        typography = typography,
        content = content
    )
}
