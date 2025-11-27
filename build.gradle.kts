// ARTE DIGITAL APP / build.gradle.kts (Nivel de PROYECTO)


// Bloque 'plugins' solo para plugins que resuelven automáticamente (como AGP)
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    // Hemos ELIMINADO: id("de.mannodermaus.android-junit5") version "1.10.0" apply false
}