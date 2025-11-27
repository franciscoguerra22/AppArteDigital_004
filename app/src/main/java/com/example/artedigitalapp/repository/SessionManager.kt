package com.example.artedigitalapp.repository

import android.content.Context

object SessionManager {

    private const val PREF_NAME = "user_session"
    private const val KEY_TOKEN = "token"
    private const val KEY_ROLE = "role"
    private const val KEY_NOMBRE = "nombre"
    private const val KEY_ID = "id" // Nueva clave

    // --- MODIFICAR saveSession para aceptar 'id' ---
    fun saveSession(context: Context, token: String?, role: String?, nombre: String?, id: Long?) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putString(KEY_ROLE, role)
            .putString(KEY_NOMBRE, nombre)
            .putLong(KEY_ID, id ?: -1) // Guardar Long. Usamos -1 si es nulo
            .apply()
    }

    // --- MODIFICAR loadSession para cargar 'id' ---
    fun loadSession(context: Context): UserSession {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

        val loadedId = prefs.getLong(KEY_ID, -1)

        UserSession.token = prefs.getString(KEY_TOKEN, null)
        UserSession.role = prefs.getString(KEY_ROLE, null)
        UserSession.nombre = prefs.getString(KEY_NOMBRE, null)
        UserSession.id = if (loadedId != -1L) loadedId else null // Asignar ID

        return UserSession
    }

    // --- MODIFICAR clearSession (opcional, pero buena práctica) ---
    fun clearSession(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
        UserSession.clear() // También limpiar el objeto en memoria
    }
}