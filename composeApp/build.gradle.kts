import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework

plugins {
    // Core plugins
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)

    // Compose Multiplatform plugin
    alias(libs.plugins.compose.compiler)

    // Serialization & Dependency Injection
    alias(libs.plugins.kotlinxSerialization)
    alias(libs.plugins.androidHilt)
    alias(libs.plugins.kmpNativeCoroutines)

    // KSP and SQLDelight
    alias(libs.plugins.ksp)
    alias(libs.plugins.sqldelight)
}

sqldelight {
    databases {
        create("AppDatabase") {
            packageName.set("org.example.apptest1.db")
        }
    }
}

kotlin {
    // Define targets
    androidTarget()
    jvmToolchain(17)
    jvm() // JVM target for desktop

    // Print target names and prevent deprecated targets
    targets.all {
        if (name.contains("iosArm32", ignoreCase = true)) {
            throw GradleException("iosArm32 should not be configured!")
        }
        println(">>> KMP Target: ${this.name}")
    }

    iosX64()       // Simulator (Intel)
    iosArm64()     // Device
    iosSimulatorArm64() // Simulator (Apple Silicon)
    // Configure XCFramework for iOS targets
    val xcframework = XCFramework()
    listOf(iosX64(), iosArm64(), iosSimulatorArm64()).forEach { target ->
        target.binaries.framework {
            baseName = "composeApp"
            isStatic = true
            xcframework.add(this)
        }
    }

    sourceSets {
        // Common source set
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(compose.components.resources)

            implementation(libs.kotlinx.serialization.core)
            implementation(libs.kotlinx.coroutines.core)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)


            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
            implementation(libs.koin.core)
            implementation(libs.koin.compose.viewmodel)
            api(libs.kmp.observable.viewmodel)

            implementation(libs.sqldelight.runtime)
            implementation(libs.sqldelight.coroutines.extensions)

            implementation(libs.navigation.compose)
        }

        // Android-specific dependencies
        androidMain.dependencies {
            implementation(libs.androidx.compose.ui.tooling.preview)

            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.viewmodel.compose)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation(libs.ktor.client.okhttp)
            implementation(libs.koin.android)

            implementation(libs.koin.androidx.compose)
            implementation(libs.androidx.room.runtime)
            implementation(libs.androidx.room.ktx)

            implementation(libs.dagger.hilt)
        }

        // iOS-specific dependencies
        val iosMain by creating {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.ktor.client.darwin)
                implementation(libs.koin.core)
                implementation(libs.sqldelight.driver)
            }
        }
        val iosX64Main by getting { dependsOn(iosMain) }
        val iosArm64Main by getting { dependsOn(iosMain) }
        val iosSimulatorArm64Main by getting { dependsOn(iosMain) }

        // Desktop-specific dependencies
        jvmMain.dependencies {
            implementation(libs.compose.desktop)
            implementation(libs.compose.ui.tooling.preview)
            implementation(libs.kotlinx.coroutines.swing)
        }

        // Enable necessary experimental APIs
        all {
            languageSettings.optIn("kotlinx.cinterop.ExperimentalForeignApi")
            languageSettings.optIn("kotlin.experimental.ExperimentalObjCName")
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

    // Exclude unnecessary metadata files from APK
    packaging.resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    // Set Java compatibility
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    // Add generated sources from KSP
    sourceSets.configureEach {
        kotlin.srcDir("build/generated/ksp/${name}/kotlin")
    }

    // Point to the correct AndroidManifest location
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
}

// App-level dependencies
dependencies {
    debugImplementation(libs.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)

    implementation(libs.guava)
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    add("kspAndroid", libs.androidx.room.compiler)

    implementation(libs.dagger.hilt)
    implementation(libs.androidx.hilt.composed)
    add("kspAndroid", libs.dagger.hilt.android.compiler)
}

val copyXCFrameworkToXcode by tasks.registering(Sync::class) {
    val buildType = "Debug" // or "Debug" based on your needs
    val frameworkDir = layout.buildDirectory.dir("XCFrameworks/$buildType/composeApp.xcframework")

    from(frameworkDir)
    into(rootProject.layout.projectDirectory.dir("iosApp/Frameworks/composeApp.xcframework")) // adjust if needed
}
tasks.named("assembleXCFramework") {
    finalizedBy(copyXCFrameworkToXcode)
}