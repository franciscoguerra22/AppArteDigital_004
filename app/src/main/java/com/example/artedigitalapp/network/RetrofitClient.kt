package com.example.artedigitalapp.network

import com.example.artedigitalapp.repository.UserSession
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private const val BASE_URL = "https://artedigitalapp.onrender.com/api/"

    // 1. Interceptor: Lee el token y lo inyecta en el encabezado de la solicitud.
    private val authInterceptor = Interceptor { chain ->
        val originalRequest = chain.request()
        val token = UserSession.token // Obtener el token de sesión

        // Agregar el encabezado de autorización (Bearer Token)
        if (token != null) {
            val newRequest = originalRequest.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
            chain.proceed(newRequest)
        } else {
            // Si no hay token (e.g., login/registro), se procede con la solicitud original
            chain.proceed(originalRequest)
        }
    }

    // 2. Cliente OkHttp que usa el interceptor
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    // 3. Retrofit usa el cliente OkHttp con el Interceptor
    val apiService: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient) // <-- ¡El cliente con el interceptor!
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}