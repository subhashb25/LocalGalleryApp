rootProject.name = "LocalGalleryApp"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
                includeGroupAndSubgroups("com.google.dagger")
                includeGroupAndSubgroups("org.jetbrains.compose")
            }
        }
        mavenCentral()
        // 👇 Required for Compose Multiplatform plugins
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") {
            mavenContent {
                includeGroupAndSubgroups("org.jetbrains.compose")
            }
        }
        maven("https://maven.pkg.jetbrains.space/public/p/kotlin-plugins/release") // ✅ Add this

        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
                includeGroupAndSubgroups("com.google.dagger")
                includeGroupAndSubgroups("com.squareup")
                includeGroupAndSubgroups("org.jetbrains.compose")
            }
        }
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev") {
            mavenContent {
                includeGroupAndSubgroups("org.jetbrains.compose")
            }
        }

    }
}

include(":shared")
include(":composeApp")
include(":desktopApp")