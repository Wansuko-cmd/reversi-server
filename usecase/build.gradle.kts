plugins {
    kotlin("jvm") version "1.7.20"
}

dependencies {
    implementation(project(":domain"))
    implementation(libs.coroutine)
    testImplementation(libs.bundles.test)
}
