package com.oucrc.plugins

import io.ktor.server.application.Application

fun Application.installPlugins() {
    koinPlugins()
    serializerPlugins()
}
