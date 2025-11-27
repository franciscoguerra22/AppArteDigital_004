package com.example.artedigitalapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.artedigitalapp.repository.CurrencyRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    private val repository = CurrencyRepository()
    private val _usdRate = MutableStateFlow<Double?>(null)
    val usdRate: StateFlow<Double?> = _usdRate

    fun fetchUSDRate() {
        viewModelScope.launch {
            try {
                val response = repository.getRates()

                // Aquí estaba el error: antes usabas response.rates["USD"]
                val usd = response.conversion_rates["USD"]

                _usdRate.value = usd
            } catch (e: Exception) {
                Log.e("CURRENCY_API", "ERROR → ${e.message}", e)
                _usdRate.value = null
            }
        }
    }
}
