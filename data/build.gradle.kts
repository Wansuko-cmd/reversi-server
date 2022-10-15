plugins {
    kotlin("jvm") version "1.7.20"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":utils"))
    implementation(libs.coroutine)
    implementation(libs.bundles.database)
    implementation(libs.h2)

    testImplementation(libs.bundles.test)
}