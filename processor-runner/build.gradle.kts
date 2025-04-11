plugins {
    id("com.android.library") // ✅ Required for Hilt
    kotlin("android")         // ✅ For Kotlin Android support
    kotlin("kapt")            // ✅ For annotation processing
    id("dagger.hilt.android.plugin") // ✅ Hilt plugin
}
dependencies {
    // Hilt annotations + compiler


    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-compiler:2.48")
}

kotlin {
    jvmToolchain(17)
}