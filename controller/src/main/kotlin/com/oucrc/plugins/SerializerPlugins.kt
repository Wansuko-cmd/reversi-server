package com.oucrc.plugins

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*

fun Application.serializerPlugins() {
    install(ContentNegotiation) {
        json()
    }
}