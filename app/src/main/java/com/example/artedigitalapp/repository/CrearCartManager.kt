package com.example.artedigitalapp.utils

import android.content.Context
import com.example.artedigitalapp.models.Servicio
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object CartManager {

    private const val PREF_NAME = "cart_prefs"
    private const val KEY_CART = "cart_items"

    private val gson = Gson()

    fun saveCart(context: Context, cartItems: List<Servicio>) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = gson.toJson(cartItems)
        prefs.edit().putString(KEY_CART, json).apply()
    }

    fun loadCart(context: Context): MutableList<Servicio> {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        val json = prefs.getString(KEY_CART, null)

        return if (json != null) {
            val type = object : TypeToken<MutableList<Servicio>>() {}.type
            gson.fromJson(json, type)
        } else {
            mutableListOf()
        }
    }

    fun clearCart(context: Context) {
        val prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        prefs.edit().clear().apply()
    }
}
