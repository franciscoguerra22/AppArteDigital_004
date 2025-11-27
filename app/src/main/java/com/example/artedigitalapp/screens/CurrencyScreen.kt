package com.example.artedigitalapp.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.artedigitalapp.viewmodel.CurrencyViewModel
import java.text.DecimalFormat

@Composable
fun CurrencyScreen(viewModel: CurrencyViewModel) {
    val usdRate by viewModel.usdRate.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.fetchUSDRate()
    }

    val df = DecimalFormat("#,###")
    val dfSmall = DecimalFormat("0.000000")

    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {

        Text("Tasa de Cambio", style = MaterialTheme.typography.titleLarge)

        Spacer(Modifier.height(12.dp))

        when (usdRate) {
            null -> {
                Text("Cargando tasa...", style = MaterialTheme.typography.bodyLarge)
            }

            else -> {
                val clpPerUsd = 1 / usdRate!!  // Ej: 1 / 0.00108 = 925 CLP

                Text(
                    text = "1 USD = ${df.format(clpPerUsd)} CLP",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Spacer(Modifier.height(8.dp))

                Text(
                    text = "1 CLP = ${dfSmall.format(usdRate)} USD",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}
