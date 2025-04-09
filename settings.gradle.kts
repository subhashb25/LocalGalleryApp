// ✅ Required: Sets the root project name (used in IDE, build artifacts, etc.)
rootProject.name = "LocalGalleryApp"

// ✅ Optional but Recommended: Enables Typesafe Project Accessors (access subprojects via `libs.` syntax)
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    plugins {
        // ✅ Required: Compose Multiplatform plugin for building with Compose on Android/Desktop/iOS
        id("org.jetbrains.compose") version "1.7.3"
    }

    repositories {
        // ✅ Required: Gradle needs this to resolve plugins like AGP, Kotlin, Compose
        gradlePluginPortal() // must be early for plugin resolution

        // ✅ Required: For resolving Android-related plugins (like Hilt, AGP)
        google()

        // ✅ Required: General-purpose repo for Kotlin/Java artifacts
        mavenCentral()

        // ✅ Required: Compose Multiplatform artifacts live here
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // ✅ Required: For Android + Google libraries
        google()

        // ✅ Required: For most Kotlin libraries and dependencies (Coroutines, Ktor, etc.)
        mavenCentral()

        // ✅ Required: For JetBrains Compose artifacts
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

// ✅ Required: Includes your shared Kotlin Multiplatform logic
include(":shared")

// ✅ Required: Includes the Compose Multiplatform app (Android/iOS/desktop shared UI)
include(":composeApp")

// ✅ Optional: Include only if you have a separate desktop-specific app module
include(":desktopApp")

