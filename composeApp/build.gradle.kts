plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)
}


kotlin {
    androidTarget()
    jvmToolchain(17)
    jvm("desktop")

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(libs.compose.runtime)
                implementation(libs.compose.foundation)
                implementation(libs.compose.material3)
                implementation(libs.compose.ui)
                implementation(libs.compose.resources)
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.kotlinx.serialization.core)
            }
        }

        val androidMain by getting {
            dependencies {
                implementation(libs.compose.ui.tooling.preview)
                implementation(libs.androidx.activity.compose)
                implementation(libs.androidx.viewmodel.compose)
                implementation(libs.androidx.navigation.compose)
                implementation(libs.androidx.lifecycle.viewmodel)
                implementation(libs.androidx.lifecycle.runtime.compose)
                implementation(libs.koin.androidx.compose)
                implementation(libs.coil.compose)
                implementation(libs.coil.network.ktor)

                implementation(libs.androidx.hilt.composed)

                implementation(project(":generated-shared"))
            }
        }

        val desktopMain by getting {
            dependencies {
                implementation(libs.compose.desktop)
                implementation(libs.kotlinx.coroutines.swing)

                // ✅ Generated shared Hilt code from processor-runner
                implementation(project(":generated-shared"))
            }
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
    // ✅ Ensure Room/KSP output is used on Android
    sourceSets.configureEach {
        kotlin.srcDir("build/generated/ksp/${name}/kotlin")
    }

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

// ✅ Regular debug dependency
dependencies {
    debugImplementation(libs.compose.ui)
    implementation("com.google.guava:guava:32.1.2-jre")
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    add("kspAndroid", libs.androidx.room.compiler)
}

// ✅ Auto-sync kapt output from processor-runner
tasks.named("desktopMainClasses") {
    dependsOn(":syncGeneratedCode")
}

// ✅ Hook into the global build + assemble lifecycle
tasks.named("build") {
    dependsOn(":syncGeneratedCode")
}

tasks.named("assemble") {
    dependsOn(":syncGeneratedCode")
}
