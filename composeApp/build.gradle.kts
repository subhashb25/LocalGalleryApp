import com.google.devtools.ksp.gradle.KspExtension

plugins {
    alias(libs.plugins.kotlinMultiplatform)    // Then the multiplatform core
    alias(libs.plugins.androidApplication)     // Android-specific setup first
    alias(libs.plugins.compose)                // UI framework next
    alias(libs.plugins.kotlinxSerialization)   // Serialization after core setup
    alias(libs.plugins.androidHilt)            // DI after all relevant configurations
    alias(libs.plugins.ksp)                    // Symbol processing after language setup
    id("org.jetbrains.kotlin.kapt")            // no version added since its already pulled via another plugin i.e. Kotlin Multiplatform
}
kotlin {
    androidTarget()
    jvmToolchain(17)
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

            // Hilt Dependencies via bundle
            implementation(libs.dagger.hilt)
            implementation(libs.androidx.hilt.composed)


            // Room Database Dependencies
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)

            // 👇 Manual workaround for missing javapoet
            //implementation(libs.javapoet)
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    sourceSets.configureEach {
        kotlin.srcDir("build/generated/ksp/${name}/kotlin")
    }
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

dependencies {
    debugImplementation(libs.compose.ui)
    implementation(libs.dagger.hilt)

    implementation(libs.androidx.hilt.composed)
    // ✅ KSP for Room
    ksp(libs.androidx.room.compiler)

    add("kapt", libs.dagger.hilt.android.compiler)
    ksp(libs.dagger.hilt.compiler)
    // ✅ KAPT for Hilt
    //kapt(libs.hilt.compiler) // ✅ unwraps the dependency // Hilt must use kapt
}

configure<KspExtension> {
    arg("dagger.hilt.disableModulesHaveInstallInCheck", "true")
}


