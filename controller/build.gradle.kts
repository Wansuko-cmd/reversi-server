@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    application
    kotlin("jvm") version "1.7.20"
    alias(libs.plugins.ktor)
    alias(libs.plugins.serialization)
}

group = "com.oucrc"
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":usecase"))
    implementation(project(":data"))
    implementation(project(":utils"))
    implementation(libs.serialization)
    implementation(libs.bundles.ktor)
    implementation(libs.bundles.koin)
    implementation(libs.h2)
    implementation(libs.exposed.core)
    implementation(libs.exposed.jdbc)
    testImplementation(libs.bundles.test)
}
