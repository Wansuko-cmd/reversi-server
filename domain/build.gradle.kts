plugins {
    kotlin("jvm") version "1.7.20"
}

dependencies {
    implementation(project(":utils"))
    testImplementation(libs.bundles.test)
}