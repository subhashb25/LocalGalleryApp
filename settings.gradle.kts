rootProject.name = "LocalGalleryApp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    plugins {
        id("org.jetbrains.compose") version "1.7.3"
    }
    repositories {
        gradlePluginPortal() // ✅ must be first or early
        google()
        mavenCentral()
        // ✅ required for org.jetbrains.compose plugin
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }

}

dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

include(":shared")
include(":composeApp")
include(":desktopApp")
