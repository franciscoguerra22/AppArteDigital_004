package com.example.artedigitalapp.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.example.artedigitalapp.models.LoginRequest
import com.example.artedigitalapp.repository.AuthRepository
import com.example.artedigitalapp.repository.UserSession
import com.example.artedigitalapp.repository.SessionManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    onNavigateToRegistro: () -> Unit,
    onLoginSuccess: () -> Unit
) {

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authRepository = AuthRepository()
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {

        Text("Iniciar Sesión", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo Electrónico") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                scope.launch {
                    try {
                        val request = LoginRequest(email, password)

                        val response = withContext(Dispatchers.IO) {
                            authRepository.login(request)
                        }

                        // 1. GUARDAR EN MEMORIA DE EJECUCIÓN (UserSession)
                        UserSession.token = response.token
                        UserSession.role = response.role
                        UserSession.nombre = response.nombre
                        UserSession.id = response.id // <-- El ID se guarda aquí

                        // 2. GUARDAR SESIÓN PERSISTENTE (SessionManager)
                        SessionManager.saveSession(
                            context = context,
                            token = response.token,
                            role = response.role,
                            nombre = response.nombre,
                            id = response.id // <-- CORRECCIÓN: El ID se pasa aquí
                        )

                        Toast.makeText(context, "Sesion iniciada", Toast.LENGTH_SHORT).show()
                        onLoginSuccess()

                    } catch (e: Exception) {
                        Toast.makeText(context, e.message ?: "Error desconocido", Toast.LENGTH_LONG).show()
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(
            onClick = onNavigateToRegistro,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}