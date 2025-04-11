
buildscript {
    dependencies {
        classpath("com.google.dagger:hilt-android-gradle-plugin:2.56.1")
    }
}
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


tasks.register<Copy>("copyGeneratedSources") {
    description = "Copy generated KAPT sources from processor-runner to generated-shared module"
    group = "build"

    val generatedSrcDir = file("processor-runner/build/generated/source/kapt/main")
    val destinationDir = file("generated-shared/src/main/kotlin")

    from(generatedSrcDir)
    into(destinationDir)

    doFirst {
        if (!generatedSrcDir.exists()) {
            throw GradleException("Generated sources not found! Run build on processor-runner first.")
        }
        println("Copying generated sources from $generatedSrcDir to $destinationDir")
    }
}


// ✅ Automatically sync generated sources into generated-shared
tasks.register<Copy>("syncGeneratedCode") {
    dependsOn(":processor-runner:kaptDebugKotlin") // ✅ correct variant-specific KAPT task
    from("processor-runner/build/generated/source/kapt/debug")
    into("generated-shared/src/main/kotlin")
}
