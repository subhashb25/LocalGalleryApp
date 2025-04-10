
plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlinxSerialization) apply false
    alias(libs.plugins.kmpNativeCoroutines) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.androidHilt) apply (false)
}

configurations.all {
    resolutionStrategy {
        force("com.google.guava:guava:32.1.2-jre")
    }
}
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.56.1")
    }
}
