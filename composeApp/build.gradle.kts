import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.androidHilt)
}
kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    jvm("desktop") // defines desktopMain, desktopTest, etc.
    sourceSets {
        val desktopMain by getting
        
        androidMain.dependencies {
            // Jetpack Compose Dependencies
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.viewmodel.compose)
            implementation(libs.androidx.navigation.compose)
            implementation(libs.koin.androidx.compose)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            // Hilt Dependency Injection
            implementation(libs.androidx.hilt.composed)
            implementation(libs.androidx.hilt)

            // Room Database Dependencies
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)

        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.resources)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(projects.shared)
            implementation(libs.kotlinx.serialization.core)
        }
        desktopMain.dependencies {
            implementation(libs.compose.desktop)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "org.example.apptest1"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.apptest1"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.ui)
    add("kspAndroid", libs.androidx.room.compiler)
    add("kspAndroid", libs.hilt.compiler)


}

// Enable Hilt Annotation Processing
ksp {
    arg("dagger.hilt.disableModulesHaveInstallInCheck", "true")
}

