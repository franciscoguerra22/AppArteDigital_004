package com.example.artedigitalapp.navigation

sealed class Screens(val route: String) {

    // Rutas simples
    object Login : Screens("login_screen")
    object Registro : Screens("register_screen")
    object HomePrincipal : Screens("home_principal_screen")
    object Home : Screens("home_screen")
    object Carrito : Screens("carrito_screen")
    object ServiciosAdmin : Screens("servicios_admin_screen")
    object CrearServicio : Screens("crear_servicio_screen")

    // 🎯 NUEVO: Ruta para Mis Compras
    object MisCompras : Screens("mis_compras_screen")

    // Rutas con parámetros limpias (sin {id})
    object EditarServicio : Screens("editar_servicio_screen") {
        fun createRoute(id: Long) = "$route/$id"
    }

    object ServicioDetail : Screens("servicio_detail_screen") {
        fun createRoute(id: Long) = "$route/$id"
    }

    object UsuariosAdmin : Screens("usuarios_admin")

}