package com.example.artedigitalapp.utils

import android.content.Context
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.Build

fun vibrar(context: Context, milis: Long = 80) {
    val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        vibrator.vibrate(
            VibrationEffect.createOneShot(milis, VibrationEffect.DEFAULT_AMPLITUDE)
        )
    } else {
        vibrator.vibrate(milis)
    }
}
