package com.example.artedigitalapp.screens.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.*
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CarruselImagenes(
    items: List<CarruselItem>,
    onClick: (CarruselItem) -> Unit = {},
    autoSlide: Boolean = true
) {
    val pagerState = rememberPagerState()

    // Auto slide cada 3s
    LaunchedEffect(autoSlide) {
        if (!autoSlide) return@LaunchedEffect

        while (true) {
            delay(3000)
            val next = (pagerState.currentPage + 1) % items.size
            pagerState.animateScrollToPage(next)
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {

        HorizontalPager(
            count = items.size,
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp)
        ) { page ->

            val item = items[page]

            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .clickable { onClick(item) }
            ) {
                Box {
                    Image(
                        painter = rememberAsyncImagePainter(item.imagenUrl),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                    // TÍTULO + PRECIO ENCIMA DE LA IMAGEN
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Black.copy(alpha = 0.45f))
                            .align(Alignment.BottomCenter)
                            .padding(8.dp)
                    ) {
                        Text(
                            text = item.titulo,
                            color = Color.White,
                            style = MaterialTheme.typography.titleMedium
                        )

                        Text(
                            text = "Precio: \$${"%.0f".format(item.precio)}",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier.padding(8.dp)
        )
    }
}

// Modelo para cada slide del carrusel
data class CarruselItem(
    val titulo: String,
    val imagenUrl: String,
    val precio: Double,
    val idServicio: Long? = null
)
