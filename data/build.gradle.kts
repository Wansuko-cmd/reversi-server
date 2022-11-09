plugins {
    kotlin("jvm") version "1.7.20"
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":utils"))
    implementation(libs.coroutine)
    implementation(libs.datetime)
    implementation(libs.bundles.database)
    implementation(libs.h2)
    implementation(libs.postgresql)

    testImplementation(libs.bundles.test)
}
