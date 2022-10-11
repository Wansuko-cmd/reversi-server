@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    kotlin("jvm") version "1.7.20"
    alias(libs.plugins.ktor)
}

group = "com.oucrc"
version = "0.0.1"
application {
    mainClass.set("com.oucrc.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(libs.bundles.ktor)
    testImplementation(libs.bundles.test)
}
