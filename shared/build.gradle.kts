import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {

    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.ksp)                         // Symbol processing after language setup
    alias(libs.plugins.kmpNativeCoroutines)
    alias(libs.plugins.sqldelight)

}

kotlin {
    androidTarget()
    jvmToolchain(17)
    targets.all {
        // Fail-safe check to skip deprecated targets
        if (name.contains("iosArm32", ignoreCase = true)) {
            throw GradleException("iosArm32 should not be configured!")
        }
    }
    targets.all {
        println(">>> KMP Target: ${this.name}")
    }


    iosX64()
    iosArm64()
    iosSimulatorArm64()

    jvm()

    sourceSets {
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)
            // Room Database
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)
            implementation(libs.androidx.room.ktx)

            // Hilt Dependency Injection
            implementation(libs.dagger.hilt)
        }

        val iosMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.koin.core)
                implementation(libs.sqldelight.ios)
            }
        }
        val iosX64Main by getting { dependsOn(iosMain) }
        val iosArm64Main by getting { dependsOn(iosMain) }
        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }

        commonMain.dependencies {
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.koin.core)
            implementation(libs.kotlinx.coroutines.core)
            api(libs.kmp.observable.viewmodel)

            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines)
        }

        // Required by KMM-ViewModel
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")

        }
    }

    tasks.register("assembleXCFramework") {
        dependsOn(
            "linkDebugFrameworkIosSimulatorArm64",
            "linkDebugFrameworkIosX64",
            "linkDebugFrameworkIosArm64"
        )
    }

}

android {
    namespace = "org.example.apptest1.shared"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

}

/*Instead of ksp("..."), in Kotlin Multiplatform, you should use add("kspAndroid", "...") for Android-specific dependencies.

kspAndroid ensures that KSP processes the Room annotations only for the Android target.*/

dependencies{
    //add("kspAndroid", libs.androidx.room.compiler)
    // ✅ KSP for Room
    add("kspAndroid", libs.androidx.room.compiler)
}



