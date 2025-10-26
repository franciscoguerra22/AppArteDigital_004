package com.example.artedigitalapp.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artedigitalapp.R

@Composable
fun HomePrincipalScreen(
    onNavigateToLogin: () -> Unit,
    onNavigateToRegistro: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Puedes poner tu logo si tienes un drawable
        // Image(painter = painterResource(id = R.drawable.logo), contentDescription = "Logo", modifier = Modifier.size(120.dp))

        Text(
            text = "🎨 Arte Digital",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Servicios de arte .",
            fontSize = 16.sp,
            lineHeight = 22.sp,
            modifier = Modifier.padding(horizontal = 12.dp),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = onNavigateToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text("Iniciar Sesión", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedButton(
            onClick = onNavigateToRegistro,
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp)
        ) {
            Text("Crear Cuenta", fontSize = 18.sp)
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Sección de servicios ofrecidos (solo visual)
        Text(
            text = "Servicios que ofrecemos:",
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text("• Dibujo digital personalizado")
        Text("• Ilustración y pintura digital")
        Text("• Diseño de personajes")
        Text("• Asesorías y clases online")
    }
}
