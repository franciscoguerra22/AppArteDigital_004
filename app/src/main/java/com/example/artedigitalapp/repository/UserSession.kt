package com.example.artedigitalapp.repository


object UserSession {
    var token: String? = null
    var role: String? = null

    fun clear() {
        token = null
        role = null
    }
}
