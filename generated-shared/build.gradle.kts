plugins {
    kotlin("jvm")
}

kotlin {
    jvmToolchain(17)
}

kotlin {
    jvmToolchain(17)

    sourceSets {
        named("main") {
            kotlin.srcDir("src/main/kotlin")
        }
    }
}
